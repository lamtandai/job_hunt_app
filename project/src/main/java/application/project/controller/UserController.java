package application.project.controller;


import java.util.List;
import java.util.Optional;

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

import application.project.domain.DTO.ResultReturnedDTO;
import application.project.domain.DTO.UserAccountDTO;
import application.project.domain.DTO.UserRegisterDTO;
import application.project.domain.User.User;
import application.project.service.UserService;


@RestController
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserRegisterDTO userDTO){
        return this.userService.handleCreateUser(userDTO)
            .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(newUser))
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return this.userService.handleGetOneUser(id)
            .map(user -> ResponseEntity.status(HttpStatus.ACCEPTED).body(user))
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/users")
    public ResponseEntity<ResultReturnedDTO> getAllUsers( 
            @RequestParam Optional<String> page,
            @RequestParam Optional<String> size) {

        String currentPage = page.isPresent() ? page.get() : "";
        String pageSize = size.isPresent() ? size.get() : "";

        ResultReturnedDTO result = this.userService.handleGetAllUser(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserAccountDTO userAccountDto){
        return this.userService. handleUpdateUser(id, userAccountDto)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<User> userRegister(@RequestBody UserRegisterDTO userRegisterDto){
        return this.userService.handleCreateUser(userRegisterDto)
            .map(newUser -> ResponseEntity.status(HttpStatus.CREATED).body(newUser))
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
    }
}
