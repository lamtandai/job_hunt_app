package application.project.domain.DTO;

public class UserAccountDTO {

    private String password;
    private String email;
    private String avatar;
    private String phone;
    private String address;
    

    public UserAccountDTO( String password, String email, String avatar, String phone,
            String address) {
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
  
   
}
