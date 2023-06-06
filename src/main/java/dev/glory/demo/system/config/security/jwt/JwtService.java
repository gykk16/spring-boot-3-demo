package dev.glory.demo.system.config.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import dev.glory.demo.system.annotation.ExcludeLogTrace;
import dev.glory.demo.system.config.security.properties.JwtProperties;
import dev.glory.demo.web.domain.RefreshToken;
import dev.glory.demo.web.domain.User;
import dev.glory.demo.web.repository.RefreshTokenRepository;
import dev.glory.demo.web.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties          jwtProperties;
    private final JwtEncoder             jwtEncoder;
    private final JwtDecoder             jwtDecoder;
    private final UserRepository         userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @ExcludeLogTrace
    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    @ExcludeLogTrace
    public String extractRole(String token) {
        return extractRole(decodeToken(token));
    }

    @ExcludeLogTrace
    public String extractRole(Jwt jwt) {
        return jwt.getClaimAsString("scope");
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> !authority.startsWith("ROLE"))
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.accessTokenExpireMin(), ChronoUnit.MINUTES))
                .subject(user.getUsername())
                .claim("scope", user.getRole())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Transactional
    public String generateRefreshToken(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Optional<RefreshToken> userRefreshTokenOpt = refreshTokenRepository.findByUser(user);

        if (userRefreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = userRefreshTokenOpt.get();
            refreshToken.updateRefreshToken(UUID.randomUUID().toString(),
                    LocalDateTime.now().plusMinutes(jwtProperties.refreshTokenExpireMin()));

            return refreshToken.getRefreshToken();
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .refreshToken(UUID.randomUUID().toString())
                .expireDt(LocalDateTime.now().plusMinutes(jwtProperties.refreshTokenExpireMin()))
                .build();

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getRefreshToken();
    }
}
