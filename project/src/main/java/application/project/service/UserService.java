package application.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import application.project.domain.DTO.UserAccountDTO;
import application.project.domain.DTO.UserRegisterDTO;
import application.project.domain.User.User;
import application.project.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> handleCreateUser(UserRegisterDTO userDto) {
        String hash_password = this.passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(hash_password);
        return this.userRepository.createUser(userDto).flatMap(this::handleGetOneUser);
    }

    public List<User> handleGetAllUser() {
        return this.userRepository.findAll().get();
    }

    public Optional<User> handleGetOneUser(long id) {
        return this.userRepository.find(id);
    }

    public Optional<User> handleGetOneUserByUsername(String username){
        return this.userRepository.findByUserName(username);
    }

    public Optional <User> handleUpdateUser(long id, UserAccountDTO dto){
        return this.userRepository.find(id).map(
            user -> {
                if (dto.getEmail()    != null) user.setEmail(dto.getEmail());
                if (dto.getPassword() != null) user.setPassword(dto.getPassword());
                if (dto.getAvatar()   != null) user.setAvatar(dto.getAvatar());
                if (dto.getPhone()    != null) user.setPhone(dto.getPhone());
                return this.userRepository.update(user).orElseThrow();
            }
        );
    }

    

}
