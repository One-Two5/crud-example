package org.example.crudexample.controller;

import org.example.crudexample.service.GenerateTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GenerateTokenService generateTokenService;

    public AuthController(GenerateTokenService generateTokenService) {
        this.generateTokenService = generateTokenService;
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken() {
        String base64Token = generateTokenService.generateToken();
        return ResponseEntity.ok(Map.of("token", base64Token));
    }
}
