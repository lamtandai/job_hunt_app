package application.project.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.project.domain.Company.Company;
import application.project.domain.DTO.ResultReturnedDTO;
import application.project.domain.DTO.UserDTO.UserRegisterDTO;
import application.project.domain.DTO.UserDTO.UserResponseDTO;
import application.project.domain.DTO.UserDTO.UserUpdateDTO;
import application.project.domain.Exception.EmailExistException;
import application.project.domain.Exception.IdInvalidException;
import application.project.domain.User.User_account;
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

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRegisterDTO userDTO)
            throws EmailExistException {
        if (this.userService.handleUserEmailExist(userDTO.getEmail())) {
            throw new EmailExistException("This email has been existed already");
        }
        return this.userService.handleCreateUser(userDTO)
                .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(newUser)))
                .orElseThrow();

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) throws IdInvalidException {
        return this.userService.handleGetOneUser(id)
                .map(user -> ResponseEntity.status(HttpStatus.ACCEPTED).body(UserMapper.toUserResponse(user)))
                .orElseThrow(() -> new IdInvalidException(id));
    }

    @GetMapping("/users")
    public ResponseEntity<ResultReturnedDTO> getAllUsers(
            @Filterable JdbcFilterSpecification<User_account> spec,
            Pageable pageable) {

        ResultReturnedDTO result = this.userService.handleGetAllUserByFilter(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
            @RequestBody UserUpdateDTO userAccountDto) {
        return this.userService.handleUpdateUser(id, userAccountDto)
                .map(user -> ResponseEntity.ok().body(UserMapper.toUserResponse(user)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> userRegister(@Valid @RequestBody UserRegisterDTO userRegisterDto)
            throws EmailExistException {
        boolean emailExist = this.userService.handleUserEmailExist(userRegisterDto.getEmail());
        if (emailExist) {
            throw new EmailExistException("This email has been existed already!");
        }
        return this.userService.handleCreateUser(userRegisterDto)
                .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(newUser)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
    }
}
