package dev.glory.demo.web.apicontroller.auth.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.glory.demo.system.config.security.role.Role;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class RegisterRequestDTO {

    private String username;
    private String password;
    private String name;
    private String email;
    private Role   role;
}
