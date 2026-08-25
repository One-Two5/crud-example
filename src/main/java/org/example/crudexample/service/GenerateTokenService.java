package org.example.crudexample.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GenerateTokenService {

    private final Cache<String,AtomicInteger> activeTokens;
    private final int maxUses;

    public GenerateTokenService(@Value("${spring.token.ttl-minutes}") long ttlMinutes,
                                @Value("${spring.token.max-uses}") int maxUses) {
        this.maxUses = maxUses;
        this.activeTokens = Caffeine.newBuilder()
                .expireAfterWrite(ttlMinutes, TimeUnit.MINUTES)
                .build();
    }

    public String generateToken() {
        String uuid = UUID.randomUUID().toString();
        String base64Token = Base64.getEncoder().encodeToString(uuid.getBytes(StandardCharsets.UTF_8));
        activeTokens.put(base64Token, new AtomicInteger(maxUses));
        return base64Token;
    }

    public boolean validateToken(String base64Token) {
        if (base64Token == null) {
            return false;
        }
        AtomicInteger usesLeft = activeTokens.getIfPresent(base64Token);
        if (usesLeft == null) {
            return false;
        }
        int remaining = usesLeft.decrementAndGet();
        if (remaining < 0) {
            activeTokens.invalidate(base64Token);
            return false;
        }
        if (remaining == 0) {
            activeTokens.invalidate(base64Token);
        }
        return true;
    }
}
