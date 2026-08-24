package org.example.crudexample.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class GenerateTokenService {

    private final Cache<String,Boolean> activeTokens;

    public GenerateTokenService(@Value("${spring.token.ttl-minutes}") long ttlMinutes) {
        this.activeTokens = Caffeine.newBuilder()
                .expireAfterWrite(ttlMinutes, TimeUnit.MINUTES)
                .build();
    }

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
        return activeTokens.getIfPresent(base64Token) != null;
    }
}
