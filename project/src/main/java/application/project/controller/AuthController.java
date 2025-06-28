package application.project.controller;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.project.domain.UserDetailsCustom;
import application.project.domain.User_account;
import application.project.domain.dto.request.ReqLoginDTO;
import application.project.domain.dto.response.ResLoginDTO;
import application.project.domain.dto.response.RestResponse;
import application.project.service.UserService;
import application.project.util.SecurityUtil.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.validation.Valid;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
        
    public AuthController(
            AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtil,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody ReqLoginDTO loginDto) {
        // input username + password for authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // authenticate user through loadUserByUsername Method
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authentication does not save user's password
        // generate jwt token for stateless connection

        // a mechanism that allows the user have just login to use his/her account
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetailsCustom principal = (UserDetailsCustom) authentication.getPrincipal();

        ResLoginDTO.UserLoginInfo userInfo = new ResLoginDTO.UserLoginInfo(
                principal.getUser_account_id(),
                principal.getUsername());

        String access_token = this.securityUtil.generateAccessToken(userInfo);

        ResLoginDTO res = new ResLoginDTO(access_token, userInfo);
        
        // create refresh token
        String refresh_token = this.securityUtil.generateRefreshToken(res);

        this.userService.handleUpdateUserToken(refresh_token, principal.getUsername());
        
        ResponseCookie resCookie = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtil.getRefreshTokenValidityInSeconds())
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(
                        RestResponse
                        .builder()
                        .setStatusCode(HttpStatus.OK.value())
                        .setData(res)
                        .setMessage("Jwt token")
                        .build());
    }

    @GetMapping(value="/account")
    //get account after f5 or refresh website
    public ResponseEntity<ResLoginDTO.UserGetAccount> fetchAccount(){
        long userId = Long.parseLong(SecurityUtil.getCurrentUserLogin().get());
                        
        User_account user = this.userService.handleGetOneUser(userId).get();
        
        ResLoginDTO.UserLoginInfo userInfo = new ResLoginDTO.UserLoginInfo(
                user.getUs_account_id(),
                user.getUs_name());

        ResLoginDTO.UserGetAccount userAccount = new ResLoginDTO.UserGetAccount(userInfo);

        return ResponseEntity.ok().body(userAccount);
    }

    @GetMapping(value="/refresh")
    public ResponseEntity<Object> getRefreshToken(
        @CookieValue(name ="refresh_token") String refresh_token 
    ) throws ServletException{  
        String userName;
        try {
            Jwt decodeToken = this.securityUtil.decodeRefreshToken(refresh_token);
            userName = decodeToken.getSubject();
        } catch (Exception e) {
                throw e;
        }

        Optional <User_account> user = this.userService.handleGetOneUserByUsername(userName);
        
        if (user.isPresent()){
                User_account realUser = user.get();
                ResLoginDTO.UserLoginInfo userInfo = new ResLoginDTO.UserLoginInfo(
                     realUser.getUs_account_id(),
                     realUser.getUs_name());

        String access_token = this.securityUtil.generateAccessToken(userInfo);

        ResLoginDTO res = new ResLoginDTO(access_token, userInfo);
        
        ResponseCookie resCookie = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtil.getRefreshTokenValidityInSeconds())
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(
                        RestResponse
                        .builder()
                        .setStatusCode(HttpStatus.OK.value())
                        .setData(res)
                        .setMessage("Jwt token")
                        .build());
        }

        throw new ServletException();
        
    }

    @PostMapping(value= "/logout")
    public ResponseEntity<Object> logOut(){
        long userId = Long.parseLong(SecurityUtil.getCurrentUserLogin().get());

        this.userService.handleDeleteUserRefreshToken(userId);

        ResponseCookie resCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(
                        RestResponse
                        .builder()
                        .setStatusCode(HttpStatus.OK.value())
                        .setMessage("Log out successfully!")
                        .build());
        }

}

