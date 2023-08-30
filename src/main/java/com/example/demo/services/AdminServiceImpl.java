package com.example.demo.services;


import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    public final AuthUserRepository authUserRepository;
    @Override
    public void setRoleAdmin(String email) {
        try {
            AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
            authUser.setRole(Auditable.Role.ADMIN);
            authUserRepository.save(authUser);
            log.info("{} admin",authUser);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void removeRoleAdmin(String email) {
        try {
            AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
            authUser.setRole(Auditable.Role.CUSTOMER);
            authUserRepository.save(authUser);
            log.info("{} removed from admins",authUser);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void setRoleAgent(String email) {
         try {
             AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
             authUser.setRole(Auditable.Role.AGENT);
             authUserRepository.save(authUser);
             log.info("{} agent",authUser);
         }catch (Exception e){
             e.printStackTrace();
             log.info("{}", Arrays.toString(e.getStackTrace()));
         }
    }

    @Override
    public void removeRoleAgent(String email) {
         try {
             AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
             authUser.setRole(Auditable.Role.CUSTOMER);
             authUserRepository.save(authUser);
             log.info("{} removed from agents",authUser);
         }catch (Exception e){
             e.printStackTrace();
             log.info("{}", Arrays.toString(e.getStackTrace()));
         }
    }
}
