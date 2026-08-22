package org.example.crudexample.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GenerateTokenService {

    private final Map<String, Boolean> activeTokens = new ConcurrentHashMap<>();

    public String generateToken() {
        String uuid = UUID.randomUUID().toString();
        String base64Token = Base64.getEncoder().encodeToString(uuid.getBytes(StandardCharsets.UTF_8));
        activeTokens.put(base64Token, Boolean.TRUE);
        return base64Token;
    }

    public boolean validateToken(String base64Token) {
        if (base64Token == null) {
            return false;
        }
        return activeTokens.containsKey(base64Token);
    }
}
