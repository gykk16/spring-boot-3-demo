package dev.glory.demo.web.apicontroller.auth;

import static dev.glory.demo.common.code.SuccessCode.CREATED;
import static dev.glory.demo.common.code.SuccessCode.SUCCESS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.RestResponseEntity;
import dev.glory.demo.system.annotation.logrequest.LogRequest;
import dev.glory.demo.system.annotation.logrequest.RequestAction;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationRequestDTO;
import dev.glory.demo.web.apicontroller.auth.dto.RefreshTokenRequestDTO;
import dev.glory.demo.web.apicontroller.auth.dto.RegisterRequestDTO;
import dev.glory.demo.web.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class ApiAuthController {

    private final AuthService authService;

    /**
     * 등록
     *
     * @param requestDTO 등록 요청
     * @return 인증 토큰 정보
     */
    @PostMapping("/register")
    @LogRequest(action = RequestAction.REGISTER)
    public ResponseEntity<RestResponseEntity> register(@Validated @RequestBody RegisterRequestDTO requestDTO) {
        return RestResponseEntity.of(CREATED, "", authService.register(requestDTO));
    }

    /**
     * 인증
     *
     * @param requestDTO 인증 요청
     * @return 인증 토큰 정보
     */
    @PostMapping("/authenticate")
    @LogRequest(action = RequestAction.AUTHENTICATE)
    public ResponseEntity<RestResponseEntity> authenticate(
            @Validated @RequestBody AuthenticationRequestDTO requestDTO) {
        return RestResponseEntity.of(SUCCESS, authService.authenticate(requestDTO));
    }

    /**
     * 토큰 재발급
     *
     * @param requestDTO 토큰 재발급 요청
     * @return 인증 토큰 정보
     */
    @PostMapping("/refresh-token")
    @LogRequest(action = RequestAction.REFRESH_TOKEN)
    public ResponseEntity<RestResponseEntity> refreshToken(@Validated @RequestBody RefreshTokenRequestDTO requestDTO) {
        return RestResponseEntity.of(SUCCESS, authService.validateRefreshToken(requestDTO.getRefreshToken()));
    }

    /**
     * Refresh Token 삭제
     *
     * @param username 사용자 id
     * @return 성공
     */
    @DeleteMapping("/refresh-token/{username}")
    @PreAuthorize("hasRole('SUPER')")
    @LogRequest(action = RequestAction.DELETE_REFRESH_TOKEN)
    public ResponseEntity<RestResponseEntity> deleteRefreshToken(@PathVariable String username) {
        authService.deleteRefreshToken(username);
        return RestResponseEntity.of(SUCCESS);
    }
}
