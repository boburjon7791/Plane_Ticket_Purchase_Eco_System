package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.ActivateCodes;
import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.repositories.ActivateCodesRepository;
import com.example.demo.repositories.AuthUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    public final AuthUserRepository authUserRepository;
    private final ActivateCodesRepository activateCodesRepository;

    @Override
    public Auditable register(@NonNull AuthUserDto authUserDto){
        try {
            AuthUser authUser =
                    AuthUserMapper.AUTH_USER_MAPPER.toEntity(authUserDto);

            AuthUser saved = authUserRepository.save(authUser);
            UUID id = saved.getId();
            String email = saved.getEmail();
            ActivateCodes activateCodes = ActivateCodes.builder()
                    .authUser(authUser).build();
            String specialMessage = """
                    This is confirmation code.
                    Do not give this code to any person.
                    """;
            Integer code = activateCodes.getCode();
            activateCodesRepository.save(activateCodes);
            // TODO: 29/08/2023 sent email activation code
            return saved;
        }catch (Exception e){
            e.printStackTrace();
            //todo
            throw new RuntimeException();
        }
    }
    @Override
    @Transactional
    public void activate(@NonNull Integer code, @NonNull String email, HttpServletResponse res){
        try {
            ActivateCodes byCode = activateCodesRepository.findByCode(code);
            check(byCode);
            AuthUser authUser = byCode.getAuthUser();
            if (authUser.getEmail().equals(email)) {
                authUser.setBlocked(true);
                authUserRepository.save(authUser);
                res.setStatus(200);
            }else {
                res.setStatus(400);
            }
        }catch (ConstraintViolationException ex){
            res.setStatus(400);
        }catch (Exception e){
            e.printStackTrace();
            res.setStatus(500);
            // TODO: 29/08/2023
        }
    }
    private static void check(@Valid ActivateCodes activateCodes){}

    @Override
    @Async
    public void generateAgainActivationCode(@NonNull String email) {
        AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
        ActivateCodes activateCodes = ActivateCodes.builder()
                .authUser(authUser)
                .build();
        activateCodesRepository.save(activateCodes);
        // TODO: 29/08/2023 send code to mail
    }
}
