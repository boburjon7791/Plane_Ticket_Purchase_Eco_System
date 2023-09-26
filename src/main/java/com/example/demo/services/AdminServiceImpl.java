package com.example.demo.services;


import com.example.demo.repositories.AuthUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            authUserRepository.setAdminRole(email);
            log.info("{} admin",email);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void removeRoleAdmin(String email) {
            authUserRepository.removeAdminRole(email);
            log.info("{} removed from admins",email);
    }

    @Override
    public void setRoleAgent(String email, String companyId) {
             authUserRepository.setAgentRole(UUID.fromString(companyId),email);
             log.info("{} agent",email);
    }

    @Override
    public void removeRoleAgent(String email) {
             authUserRepository.removeAgentRole(email);
             log.info("{} removed from agents",email);
    }

    @Override
    public void block(@NonNull String mail) {
            authUserRepository.blockUserById(mail);
            log.info("{} shu id lik user blocklandi",mail);
    }

    @Override
    public void unblock(@NonNull String mail) {
            authUserRepository.unblockUserById(mail);
            log.info("{} shu id lik user blockdan chiqarildi",mail);
    }
}
