package application.project.domain.User;
import java.time.LocalDate;

import application.project.domain.Enumeration.UserRole.UserRole;


public class User {
    private long user_account_id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String phone;
    private Integer company_id;
    private UserRole user_role;
    private LocalDate created_at;
    private LocalDate updated_at;

    public User(long user_account_id, String username, String password, String email, String avatar, String status,
            String phone, Integer company_id, UserRole user_role, LocalDate created_at,
            LocalDate updated_at) {
        this.user_account_id = user_account_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.phone = phone;
        this.company_id = company_id;
        this.user_role = user_role;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User() {}

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

    public UserRole getUserRole() {
        return user_role;
    }

    public void setUserRole(UserRole user_role) {
        this.user_role = user_role;
    }

    public LocalDate getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(long user_account_id) {
        this.user_account_id = user_account_id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }
}
