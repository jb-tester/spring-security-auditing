package com.mytests.spring.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MyController {

    @GetMapping("/for_all")
    public String forAll( ) {
        return "for all";
    }

    @GetMapping("/for_adults")
    public String forAdults( ) {
        return "for parents";
    }

    @GetMapping("/secret")
    public String secret( ) {
        return "for admins only";
    }

    @GetMapping("/secured0")
    public String secured( ) {
        return "for admins only";
    }

}
