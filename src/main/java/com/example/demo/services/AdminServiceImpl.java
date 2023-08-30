package com.example.demo.services;


import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    public final AuthUserRepository authUserRepository;
    @Override
    public void setRoleAdmin(String email) {
        try {
            AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
            authUser.setRole(Auditable.Role.ADMIN);
            authUserRepository.save(authUser);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023 log
        }
    }

    @Override
    public void removeRoleAdmin(String email) {
        try {
            AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
            authUser.setRole(Auditable.Role.CUSTOMER);
            authUserRepository.save(authUser);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023  log
        }
    }

    @Override
    public void setRoleAgent(String email) {
         try {
             AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
             authUser.setRole(Auditable.Role.AGENT);
             authUserRepository.save(authUser);
         }catch (Exception e){
             e.printStackTrace();
             // TODO: 30/08/2023 log
         }
    }

    @Override
    public void removeRoleAgent(String email) {
         try {
             AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
             authUser.setRole(Auditable.Role.CUSTOMER);
             authUserRepository.save(authUser);
         }catch (Exception e){
             e.printStackTrace();
             // TODO: 30/08/2023  log
         }
    }
}
