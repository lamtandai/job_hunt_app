package application.project.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import application.project.domain.Enumeration.UserRole.UserRole;
import application.project.domain.User.User_account;
import application.project.domain.UserDetailsCustom;

@Component
public class UserDetailsCustomService implements UserDetailsService{
    private final UserService userService;
    public UserDetailsCustomService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        Optional <User_account> userOpt = this.userService.handleGetOneUserByUsername(username);
           
        if (userOpt.isPresent()){
            User_account currentUser = userOpt.get();
            return new UserDetailsCustom(
                currentUser.getUser_account_id(),
                currentUser.getUsername(),
                currentUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(UserRole.Job_seeker.name()))
            );
        }
        
        throw new UsernameNotFoundException("User not Found");
    }
}
