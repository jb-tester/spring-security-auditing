package com.mytests.spring.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * *
 * <p>Created by irina on 31.05.2021.</p>
 * <p>Project: spring-security-auditing</p>
 * *
 */
@RestController
public class MyController {

    @GetMapping("/home")
    public String home( ) {
        return "for parents and childs";
    }
    @GetMapping("/for_all")
    public String forAll( ) {
        return "for all";
    }
    @GetMapping("/secret")
    public String secret( ) {
        return "for admins only";
    }


}
