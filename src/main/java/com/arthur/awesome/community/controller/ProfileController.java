package com.arthur.awesome.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile/question")
    public String question() {
        return "profile";
    }
}
