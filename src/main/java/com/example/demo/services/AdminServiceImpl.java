package com.example.demo.services;


import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Company;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CompanyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    public final AuthUserRepository authUserRepository;
    @Override
    public void setRoleAdmin(String email) {
        try {
            authUserRepository.setAdminRole(email);
            log.info("{} admin",email);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void removeRoleAdmin(String email) {
        try {
            authUserRepository.removeAdminRole(email);
            log.info("{} removed from admins",email);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void setRoleAgent(String email, String companyId) {
         try {
             authUserRepository.setAgentRole(email, companyId);
             log.info("{} agent",email);
         }catch (Exception e){
             e.printStackTrace();
             log.info("{}", Arrays.toString(e.getStackTrace()));
         }
    }

    @Override
    public void removeRoleAgent(String email) {
         try {
             authUserRepository.removeAgentRole(email);
             log.info("{} removed from agents",email);
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
