package application.project.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import application.project.domain.Enumeration.UserRole.UserRole;
import application.project.domain.User.User;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService{
    private final UserService userService;

    public UserDetailsCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional <User> userOpt = this.userService.handleGetOneUserByUsername(username);

        if (userOpt.isPresent()){
            User currentUser = userOpt.get();
            return new org.springframework.security.core.userdetails.User(
                currentUser.getUsername(),
                currentUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(UserRole.Job_seeker.name()))
            );
        }
        throw new UsernameNotFoundException("User Not found!");
    }
    
}
