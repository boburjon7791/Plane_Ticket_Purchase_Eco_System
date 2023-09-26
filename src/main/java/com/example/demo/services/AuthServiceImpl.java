package com.example.demo.services;

import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.entities.ActivateCodes;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Company;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.repositories.ActivateCodesRepository;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CompanyRepository;
import com.example.demo.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CompanyRepository companyRepository;
    public final AuthUserRepository authUserRepository;
    private final ActivateCodesRepository activateCodesRepository;
    private final JavaMailSenderService javaMailSenderService;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String specialMessage = """
                    This is confirmation code.
                    Do not give this code to any person.
                    """;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void register(@Valid @NonNull AuthUserDtoR authUserDtoR,
                         HttpServletResponse res,
                         HttpServletRequest req){
        if(authUserRepository.existEmail(authUserDtoR.getEmail())){
            res.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
            Optional<Company> byId= Optional.empty();
            if (authUserDtoR.companyId!=null) {
                UUID companyId = authUserDtoR.getCompanyId();
                authUserDtoR.setBlocked(true);
                byId = companyRepository.findById(companyId);
            }
            AuthUser authUser =
                    authUserMapper.toEntity(authUserDtoR,byId.orElse(null));
            authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
            authUser.setBlocked(true);
            AuthUser saved = authUserRepository.save(authUser);
            String email = saved.getEmail();
            ActivateCodes activateCodes = ActivateCodes.builder()
                    .authUser(authUser).build();
            System.out.println("when generated activateCodes.getValid() = " + activateCodes.getValid());

            Integer code = activateCodes.getCode();
            System.out.println("email = " + email);
            System.out.println("code = " + code);
            activateCodesRepository.save(activateCodes);
            javaMailSenderService.send(activateCodes,specialMessage);
            JwtTokenUtil.addCookie(req,res,"email",saved.getEmail());
            authUserMapper.toDto(saved);
            log.info("{} saved",authUser);
    }
    @Override
    @Transactional
    public void activate(@NonNull Integer code, @NonNull String email,
                         HttpServletRequest req,HttpServletResponse res) throws ConstraintViolationException {
        ActivateCodes byCode = activateCodesRepository.findById(code)
                .orElseThrow(NotFoundException::new);
            System.out.println("when activate byCode.getValid() = " + byCode.getValid());
            if (byCode.getValid().isBefore(LocalDateTime.now())) {
                res.setStatus(400);
                activateCodesRepository.deleteByCode(code);
                return;
            }
            AuthUser authUser = byCode.getAuthUser();
            String email1 = authUser.getEmail();
            System.out.println("email1 = " + email1);
            if (authUser.getEmail().equals(email)) {
                authUser.setBlocked(false);
                authUserRepository.save(authUser);
                res.setStatus(200);
                log.info("{} acivated",authUser);
                JwtTokenUtil.removeCookie(req,res,"email",authUser.getEmail());
            }else {
                res.setStatus(400);
            }
    }

    @Override
    @Async
    public void generateAgainActivationCode(@NonNull String email, HttpServletRequest req, HttpServletResponse res) {
            AuthUser authUser = authUserRepository.findByEmailSpecial(email);
            ActivateCodes activateCodes = ActivateCodes.builder()
                    .authUser(authUser)
                    .build();
            authUser.getActivateCodes().forEach(System.out::println);
            activateCodesRepository.save(activateCodes);
            JwtTokenUtil.addCookie(req,res,"email",authUser.getEmail());
            javaMailSenderService.send(activateCodes,specialMessage);
            log.info("generated activation code for {}",authUser);
    }

    @Override
    public AuthUserDtoR login(String email, String password, HttpServletResponse res) {
        String s = jwtTokenUtil.generateToken(email, password);
        res.setHeader("Authorization","Bearar "+s);
        AuthUser byEmailSpecial = authUserRepository.findByEmailSpecial(email);
        return authUserMapper.toDto(byEmailSpecial);
    }

    @Override
    public AuthUserDtoR get(UUID id) {
            AuthUser authUser = authUserRepository.findById(id)
                    .orElseThrow(NotFoundException::new);
            log.info("{} get info about self",authUser);
            return authUserMapper.toDto(authUser);
    }
}
