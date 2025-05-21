package application.project.controller;


import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.project.domain.DTO.UserRegisterDTO;
import application.project.domain.User;
import application.project.service.UserService;

@RestController
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody UserRegisterDTO userDTO){
        Optional<User> newUserOpt = this.userService.handleCreateUser(userDTO);
        if (newUserOpt.isPresent()){
            User newUser = newUserOpt.get();
            return newUser.toString();
        } 
        
        return "No user created!";
    }
}
