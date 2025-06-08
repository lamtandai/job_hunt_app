package application.project.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class UserDetailsCustom implements UserDetails{
    
    private final Long user_account_id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    
    public UserDetailsCustom(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user_account_id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UserDetailsCustom(Long id, String username, Collection<GrantedAuthority> authorities) {
        this.user_account_id = id;
        this.username = username;
        this.password = null;
        this.authorities = authorities;
    }

    public Long getUser_account_id() {
        return this.user_account_id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
    

