package application.project.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.project.domain.dto.request.ReqUserRegisterDTO;
import application.project.domain.dto.request.ReqUserUpdateDTO;
import application.project.domain.dto.response.ResUserDTO;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.domain.User_account;
import application.project.domain.Exception.EmailExistException;
import application.project.domain.Exception.InvalidException;
import application.project.domain.dto.response.RestResponse;
import application.project.repository.JdbcSpecification.JdbcFilterSpecification;
import application.project.service.UserService;
import application.project.util.CustomAnnotation.Filterable;
import application.project.util.Mapper.UserMapper;
import jakarta.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<ResUserDTO> createUser(@Valid @RequestBody ReqUserRegisterDTO userDTO)
            throws EmailExistException {
        if (this.userService.handleUserEmailExist(userDTO.getEmail())) {
            throw new EmailExistException("This email has been existed already");
        }
        if (this.userService.handleUserNameExist(userDTO.getUsername())) {
            throw new EmailExistException("This username has been existed already");
        }
        return this.userService.handleCreateUser(userDTO)
                .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(newUser)))
                .orElseThrow();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable Long id) throws InvalidException {
        return this.userService.handleGetOneUser(id)
                .map(user -> ResponseEntity.status(HttpStatus.ACCEPTED).body(UserMapper.toUserResponse(user)))
                .orElseThrow(() -> new InvalidException(String.valueOf(id)));
    }

    @GetMapping()
    public ResponseEntity<ResultReturnedDTO> getAllUsers(
            @Filterable JdbcFilterSpecification<User_account> spec,
            Pageable pageable) {

        ResultReturnedDTO result = this.userService.handleGetAllUserByFilter(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> deleteUserById(@PathVariable Long id) {
        this.userService.handleDeleteUserById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(RestResponse
                        .builder()
                        .setMessage("Delete successfully!")
                        .setStatusCode(HttpStatus.ACCEPTED.value())
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResUserDTO> updateUserById(@PathVariable Long id,
            @RequestBody ReqUserUpdateDTO userAccountDto) {
        return this.userService.handleUpdateUser(id, userAccountDto)
                .map(user -> ResponseEntity.ok().body(UserMapper.toUserResponse(user)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<ResUserDTO> userRegister(@Valid @RequestBody ReqUserRegisterDTO userRegisterDto)
            throws EmailExistException {

        if (this.userService.handleUserNameExist(userRegisterDto.getUsername())) {
            throw new EmailExistException("This username has been existed already");
        }
        boolean emailExist = this.userService.handleUserEmailExist(userRegisterDto.getEmail());
        if (emailExist) {
            throw new EmailExistException("This email has been existed already!");
        }
        return this.userService.handleCreateUser(userRegisterDto)
                .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(newUser)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
    }
}
