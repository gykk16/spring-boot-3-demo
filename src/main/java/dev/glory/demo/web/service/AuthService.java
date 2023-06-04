package dev.glory.demo.web.service;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import dev.glory.demo.common.code.AuthErrorCode;
import dev.glory.demo.common.exception.BizRuntimeException;
import dev.glory.demo.system.config.security.jwt.JwtService;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationRequestDTO;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationResponseDTO;
import dev.glory.demo.web.apicontroller.auth.dto.RegisterRequestDTO;
import dev.glory.demo.web.domain.User;
import dev.glory.demo.web.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService            jwtService;
    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final EntityManager         em;


    @Transactional
    public String register(RegisterRequestDTO registerRequestDTO) {

        boolean exists = userRepository.existsByUsername(registerRequestDTO.getUsername());
        if (exists) {
            throw new BizRuntimeException(AuthErrorCode.EXISTING_USER);
        }

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .name(registerRequestDTO.getName())
                .email(registerRequestDTO.getEmail())
                .enabled(true)
                .locked(false)
                .role(registerRequestDTO.getRole())
                .build();

        return userRepository.save(user).getUsername();
    }

    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

        return getAuthenticationResponse(authentication);
    }

    private AuthenticationResponseDTO getAuthenticationResponse(Authentication authentication) {

        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = null; // TODO: implement refresh token

        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }
}
