package dev.glory.demo.web.service;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import dev.glory.demo.common.code.AuthErrorCode;
import dev.glory.demo.common.exception.BizRuntimeException;
import dev.glory.demo.system.config.security.jwt.JwtService;
import dev.glory.demo.system.config.security.jwt.exception.CustomJwtException;
import dev.glory.demo.system.config.security.jwt.exception.TokenCode;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationRequestDTO;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationResponseDTO;
import dev.glory.demo.web.apicontroller.auth.dto.RegisterRequestDTO;
import dev.glory.demo.web.domain.RefreshToken;
import dev.glory.demo.web.domain.User;
import dev.glory.demo.web.repository.RefreshTokenRepository;
import dev.glory.demo.web.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager  authenticationManager;
    private final JwtService             jwtService;
    private final UserRepository         userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder        passwordEncoder;


    @Transactional
    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO) {

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

        userRepository.save(user);

        return getAuthenticationResponse(user);
    }

    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

        User user = userRepository.findByUsername(requestDTO.getUsername()).orElseThrow();

        return getAuthenticationResponse(user);
    }

    @Transactional
    public AuthenticationResponseDTO validateRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomJwtException(TokenCode.REFRESH_TOKEN_NOT_FOUND));

        // 만료된 토큰이면 삭제
        if (token.getExpireDt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteByUser(token.getUser());
            throw new CustomJwtException(TokenCode.REFRESH_TOKEN_EXPIRED);
        }

        User user = userRepository.findByUsername(token.getUser().getUsername()).orElseThrow();

        return getAuthenticationResponse(user);
    }

    @Transactional
    public void deleteRefreshToken(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        refreshTokenRepository.deleteByUser(user);
    }

    private AuthenticationResponseDTO getAuthenticationResponse(User user) {

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }
}
