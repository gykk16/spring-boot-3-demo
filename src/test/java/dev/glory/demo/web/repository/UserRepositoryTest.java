package dev.glory.demo.web.repository;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import dev.glory.demo.system.config.security.role.Role;
import dev.glory.demo.web.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

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
        String username = "test";

        // when
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // then
        assertThat(user.getUsername()).isEqualTo(username);
    }

    @Test
    void exists_by_username() throws Exception {
        // given
        String username = "test";

        // when
        boolean exists = userRepository.existsByUsername(username);

        // then
        assertThat(exists).isTrue();
    }
}