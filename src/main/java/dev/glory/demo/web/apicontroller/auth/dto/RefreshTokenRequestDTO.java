package dev.glory.demo.web.apicontroller.auth.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class RefreshTokenRequestDTO {

    private String refreshToken;
}
