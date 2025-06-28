package org.arfath.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MyController {

    @GetMapping("/public")
    public String publicEndpoint(){
        return "Hello, Arfath(public)";
    }

    @GetMapping("/secured")
    public String securedEndpoint(){
        return "Hello, Arfath(secured)";
    }


}
