package application.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import application.project.domain.User;
import application.project.domain.DTO.UserRegisterDTO;
import application.project.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> handleCreateUser(UserRegisterDTO userDto) {
        User newUser = User.builder()
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .email(userDto.getEmail())
            .avatar(userDto.getAvatar())
            .status(userDto.getStatus())
            .phone(userDto.getPhone())
            .build();

        return this.userRepository.createUser(newUser);
    }
    
}
