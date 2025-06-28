package org.arfath.security.controller;

import org.arfath.security.model.AuthRequest;
import org.arfath.security.model.UserEntity;
import org.arfath.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user){
        return ResponseEntity.ok(authService.register(user));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request){

        return ResponseEntity.ok(authService.login(request));
    }


}
