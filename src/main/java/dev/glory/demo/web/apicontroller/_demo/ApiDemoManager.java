package dev.glory.demo.web.apicontroller._demo;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo/manager")
public class ApiDemoManager {

    @GetMapping
    public String managerRead(Authentication authentication) {
        return authentication.getName() + " from manager endpoint";
    }
}
