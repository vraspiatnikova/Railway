package com.tsystems.jschool.railway.security;

import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User userInfo = userService.findUserByEmail(email);
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority(userInfo.getRole().name()));
            UserDetails userDetails =
                    new org.springframework.security.core.userdetails.User(userInfo.getEmail(),
                            userInfo.getPassword(), true, true, true, true,
                            roles);
            System.out.println(userDetails.getAuthorities());
            return userDetails;
        } catch (ServiceException e) {
            throw new UsernameNotFoundException(e.getError().getMessage());
        }
    }
}
