package dev.glory.demo.web.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    @ResponseBody
    public String home(Principal principal) {
        return "home " + principal.getName();
    }
}
