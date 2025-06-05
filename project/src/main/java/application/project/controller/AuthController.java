package application.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.project.domain.DTO.LoginDTO;
import application.project.domain.RestResponse.RestResponse;
import application.project.util.SecurityUtil.SecurityUtil;
import jakarta.validation.Valid;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;

    public AuthController(
        AuthenticationManagerBuilder authenticationManagerBuilder,
        SecurityUtil securityUtil
        ) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping(
        value = "/login",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDto){
        // input username + password for authentication
        UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        
        // authenticate user through loadUserByUsername Method
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authentication does not save user's password
        //generate jwt token for stateless connection

        String access_token = this.securityUtil.generateJwtToken(authentication);

        // a mechanism that allows the user have just login to use his/her account
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(
            RestResponse
                .builder()
                .setStatusCode(HttpStatus.OK.value())
                .setData(access_token)
                .setMessage("Jwt token")
                .build()
            );
    }
}
