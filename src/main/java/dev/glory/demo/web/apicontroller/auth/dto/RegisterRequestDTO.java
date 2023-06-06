package dev.glory.demo.web.apicontroller.auth.dto;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.glory.demo.system.config.security.role.Role;

@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Getter
public class RegisterRequestDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private Role   role;
}
