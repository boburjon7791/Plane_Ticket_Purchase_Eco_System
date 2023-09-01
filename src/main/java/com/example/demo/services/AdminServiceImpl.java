package com.example.demo.services;


import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

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

    @Override
    public void block(@NonNull String mail) {
        try {
            authUserRepository.blockUserById(mail);
            log.info("{} shu id lik user blocklandi",mail);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void unblock(@NonNull String mail) {
        try {
            authUserRepository.unblockUserById(mail);
            log.info("{} shu id lik user blockdan chiqarildi",mail);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }
}
