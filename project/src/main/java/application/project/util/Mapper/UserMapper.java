package application.project.util.Mapper;

import application.project.domain.User.User_account;
import application.project.domain.dto.response.ResUserDTO;

public class UserMapper {
    public static ResUserDTO toUserResponse(User_account user){
        return new ResUserDTO(user);
    }

}
