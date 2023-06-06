package dev.glory.demo.web.apicontroller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

@AllArgsConstructor
@Getter
public class AuthenticationResponseDTO {

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;
}
