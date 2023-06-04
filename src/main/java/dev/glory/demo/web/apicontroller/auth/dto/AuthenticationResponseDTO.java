package dev.glory.demo.web.apicontroller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponseDTO {

    private String accessToken;
    private String refreshToken;
}
