package dev.glory.demo.system.config.security.jwt;


import static dev.glory.demo.common.util.LogStringUtil.logTitle;
import static dev.glory.demo.system.config.security.jwt.exception.TokenCode.NO_TOKEN;
import static dev.glory.demo.system.config.security.jwt.exception.TokenCode.TOKEN_VALIDATION_ERROR;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.constant.ApplicationConst;
import dev.glory.demo.system.config.security.jwt.exception.CustomJwtException;
import dev.glory.demo.system.config.security.role.Role;
import org.slf4j.MDC;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        log.info(logTitle("JwtAuthenticationFilter"));

        String username = null;
        try {
            final String jwtToken = resolveToken(request).orElseThrow(() -> new CustomJwtException(NO_TOKEN));

            Jwt jwt = jwtService.decodeToken(jwtToken);
            username = jwt.getSubject();

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String roleName = jwtService.extractRole(jwt);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Role.valueOf(roleName).getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.info("# ==> Auth Info , username = {} , role = {}", username, Role.valueOf(roleName).getAuthorities());
            }

        } catch (CustomJwtException e) {
            request.setAttribute("exception", e);

        } catch (JwtException e) {
            request.setAttribute("exception", new CustomJwtException(TOKEN_VALIDATION_ERROR, e.getMessage()));

        } catch (Exception e) {
            // JwtAuthenticationEntryPoint 에서 처리

        } finally {
            MDC.put(ApplicationConst.ACCESS_ID, username);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            log.debug("# ==> Authorization = {}", authHeader);
            return authHeader.substring(7).describeConstable();
            // fast exit
        }
        return Optional.empty();
    }
}
