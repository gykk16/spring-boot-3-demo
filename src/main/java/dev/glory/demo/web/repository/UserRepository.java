package dev.glory.demo.web.repository;

import java.util.Optional;

import dev.glory.demo.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);


}