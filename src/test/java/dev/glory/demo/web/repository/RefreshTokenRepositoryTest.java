package dev.glory.demo.web.repository;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import dev.glory.demo.system.config.security.role.Role;
import dev.glory.demo.web.domain.RefreshToken;
import dev.glory.demo.web.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .username("test")
                .password("mypassword123!@#")
                .name("test")
                .email("test@abc.com")
                .enabled(true)
                .locked(false)
                .role(Role.SUPER)
                .build();

        userRepository.save(user);
    }

    @Test
    void find_by_username() throws Exception {
        // given
        User user = userRepository.findByUsername("test").orElseThrow();
        String refreshToken = UUID.randomUUID().toString();

        createRefreshToken(user, refreshToken);

        // when
        RefreshToken findToken = refreshTokenRepository.findByUser(user)
                .orElseThrow();

        // then
        assertThat(findToken.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(findToken.getUser().getUsername()).isEqualTo(user.getUsername());

    }

    @Test
    void find_by_refresh_token() throws Exception {
        // given
        User user = userRepository.findByUsername("test").orElseThrow();
        String refreshToken = UUID.randomUUID().toString();

        createRefreshToken(user, refreshToken);

        // when
        RefreshToken findToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow();

        // then
        assertThat(findToken.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(findToken.getUser().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void delete_by_user() throws Exception {
        // given
        User user = userRepository.findByUsername("test").orElseThrow();
        String refreshToken = UUID.randomUUID().toString();

        createRefreshToken(user, refreshToken);

        // when
        refreshTokenRepository.deleteByUser(user);

        // then
        assertThat(refreshTokenRepository.findByRefreshToken(refreshToken)).isEmpty();
    }

    private void createRefreshToken(User user, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .user(user)
                .refreshToken(refreshToken)
                .expireDt(LocalDateTime.now().plusMinutes(30))
                .build();
        refreshTokenRepository.save(token);
    }
}