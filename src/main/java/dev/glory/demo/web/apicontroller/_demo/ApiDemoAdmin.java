package dev.glory.demo.web.apicontroller._demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/demo/admin")
public class ApiDemoAdmin {

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String adminRead(Authentication authentication) {
        return "GET:: admin" + authentication.getName();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String adminCreate(Authentication authentication) {
        return "POST:: admin" + authentication.getName();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String adminUpdate(Authentication authentication) {
        return "PUT:: admin" + authentication.getName();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String adminDelete(Authentication authentication) {
        return "DELETE:: admin" + authentication.getName();
    }
}
