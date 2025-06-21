package application.project.domain.dto.request;

import application.project.domain.Enumeration.UserRole.UserRole;

public class ReqUserUpdateDTO {

    private String username;
    private String password;
    private String email;
    private String avatar;
    private String phone;
    private UserRole user_role;
   

    public ReqUserUpdateDTO(String username, String email, String avatar, String phone, UserRole user_role) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.phone = phone;
        this.user_role = user_role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public UserRole getUser_role() {
        return user_role;
    }
    public void setUser_role(UserRole user_role) {
        this.user_role = user_role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  
}
