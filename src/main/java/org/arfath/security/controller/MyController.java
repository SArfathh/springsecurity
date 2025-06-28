package org.arfath.security.controller;

import org.arfath.security.model.UserEntity;
import org.arfath.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class MyController {

    public UserService userService;

    public MyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/public")
    public String publicEndpoint(){
        return "Hello, Arfath(public)";
    }

    @GetMapping("/secured")
    public String securedEndpoint(){
        return "Hello, Arfath(secured)";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user){
        return ResponseEntity.ok(userService.register(user));
    }



}
