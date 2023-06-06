package dev.glory.demo.web.repository;

import java.util.Optional;

import dev.glory.demo.web.domain.User;
import dev.glory.demo.web.domain.RefreshToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    @EntityGraph(attributePaths = "user")
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    long deleteByUser(User user);
}