package com.adhyayan.demo.service;

import com.adhyayan.demo.model.User;
import com.adhyayan.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsServices implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsServices(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(("User not found: "+username)));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
            );
    }
}
