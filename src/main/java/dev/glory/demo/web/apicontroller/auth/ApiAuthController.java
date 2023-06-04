package dev.glory.demo.web.apicontroller.auth;

import static dev.glory.demo.common.code.SuccessCode.CREATED;
import static dev.glory.demo.common.code.SuccessCode.SUCCESS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.RestResponseEntity;
import dev.glory.demo.system.annotation.logrequest.LogRequest;
import dev.glory.demo.system.annotation.logrequest.RequestAction;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationRequestDTO;
import dev.glory.demo.web.apicontroller.auth.dto.RegisterRequestDTO;
import dev.glory.demo.web.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class ApiAuthController {

    private final AuthService authService;


    @PostMapping("/register")
    @LogRequest(action = RequestAction.REGISTER)
    public ResponseEntity<RestResponseEntity> register(@RequestBody RegisterRequestDTO requestDTO) {
        return RestResponseEntity.of(CREATED, "", authService.register(requestDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<RestResponseEntity> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        return RestResponseEntity.of(SUCCESS, authService.authenticate(requestDTO));
    }
    //
    // @PostMapping("/refresh-token")
    // @LogRequest(action = RequestAction.REFRESH_TOKEN)
    // public ResponseEntity<RestResponseEntity> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
    //     return RestResponseEntity.of(SUCCESS, authService.validateRefreshToken(request.getRefreshToken()));
    // }
}
