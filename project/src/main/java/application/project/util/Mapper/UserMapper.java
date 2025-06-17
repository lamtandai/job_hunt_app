package application.project.util.Mapper;

import application.project.domain.DTO.UserDTO.UserResponseDTO;
import application.project.domain.User.User_account;

public class UserMapper {
    public static UserResponseDTO toUserResponse(User_account user){
        return new UserResponseDTO(user);
    }

}
