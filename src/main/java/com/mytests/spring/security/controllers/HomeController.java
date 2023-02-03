package com.mytests.spring.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * *
 * <p>Created by irina on 2/3/2023.</p>
 * <p>Project: spring-security-auditing</p>
 * *
 */
@Controller
public class HomeController {

    @GetMapping({"/home","/"})
    public String home(ModelMap model) {
        model.addAttribute("home", "hello! This page is both for parents and childs");
        return "home";
    }
}
