package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.ActivateCodes;
import com.example.demo.entities.AuthUser;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.repositories.ActivateCodesRepository;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JavaMailSenderService javaMailSenderService;
    private static final String specialMessage = """
                    This is confirmation code.
                    Do not give this code to any person.
                    """;

    @Override
    public AuthUserDto register(@NonNull AuthUserDto authUserDto,
                                HttpServletResponse res,
                                HttpServletRequest req){
        try {
            AuthUser authUser =
                    AuthUserMapper.AUTH_USER_MAPPER.toEntity(authUserDto);

            AuthUser saved = authUserRepository.save(authUser);
            UUID id = saved.getId();
            String email = saved.getEmail();
            ActivateCodes activateCodes = ActivateCodes.builder()
                    .authUser(authUser).build();

            Integer code = activateCodes.getCode();
            System.out.println("email = " + email);
            System.out.println("code = " + code);
            activateCodesRepository.save(activateCodes);
            javaMailSenderService.send(activateCodes,specialMessage);
            JwtTokenUtil.addCookie(req,res,"email",saved.getEmail());
            return AuthUserMapper.AUTH_USER_MAPPER.toDto(saved);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            throw new RuntimeException();
        }
    }
    @Override
    @Transactional
    public void activate(@NonNull Integer code, @NonNull String email,
                         HttpServletRequest req,HttpServletResponse res) throws ConstraintViolationException {
        ActivateCodes byCode=null;
        try {
            byCode = activateCodesRepository.findByCode(code);
            check(byCode);
            AuthUser authUser = byCode.getAuthUser();
            if (authUser.getEmail().equals(email)) {
                authUser.setBlocked(true);
                authUserRepository.save(authUser);
                res.setStatus(200);
                activateCodesRepository.deleteByCode(code);
                JwtTokenUtil.removeCookie(req,res,"email",authUser.getEmail());
            }else {
                res.setStatus(400);
            }
        } catch (Exception e){
            e.printStackTrace();
            res.setStatus(500);
            // TODO: 29/08/2023 log
        }
    }
    private static void check(@Valid ActivateCodes activateCodes){}

    @Override
    @Async
    public void generateAgainActivationCode(@NonNull String email, HttpServletRequest req, HttpServletResponse res) {
        try {
            AuthUser authUser = authUserRepository.findByEmailAndBlockedFalse(email);
            ActivateCodes activateCodes = ActivateCodes.builder()
                    .authUser(authUser)
                    .build();
            check(activateCodes);
            activateCodesRepository.save(activateCodes);
            JwtTokenUtil.addCookie(req,res,"email",authUser.getEmail());
            javaMailSenderService.send(activateCodes,specialMessage);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
        }
    }
}
