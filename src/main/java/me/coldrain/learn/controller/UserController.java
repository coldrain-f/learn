package me.coldrain.learn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String signInForm() {
        return "authentication/sign-in";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "authentication/sign-up";
    }
}
