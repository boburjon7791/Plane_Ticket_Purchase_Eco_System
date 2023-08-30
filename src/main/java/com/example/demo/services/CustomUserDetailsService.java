package com.example.demo.services;

import com.example.demo.entities.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
            return User.builder()
                        .username(email)
                        .password(authUser.getPassword())
                        .credentialsExpired(false)
                        .roles(authUser.getRole().name())
                        .accountLocked(authUser.getBlocked())
                        .accountExpired(false)
                        .build();
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
}
