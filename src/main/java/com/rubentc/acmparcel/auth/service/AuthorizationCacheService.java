package com.rubentc.acmparcel.auth.service;

import com.rubentc.acmparcel.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorizationCacheService {

    private static final String KEY_PREFIX = "auth:authorities:";

    private static final Duration CACHE_TTL =
            Duration.ofMinutes(10);

    private final StringRedisTemplate redisTemplate;

    public Set<String> getAuthorities(UUID userId) {

        String key = buildKey(userId);

        Set<String> authorities =
                redisTemplate.opsForSet().members(key);

        return authorities != null
                ? authorities
                : Set.of();
    }

    public void cacheAuthorities(
            UUID userId,
            Set<String> authorities
    ) {
        if (authorities.isEmpty()) {
            return;
        }

        String key = buildKey(userId);

        redisTemplate.opsForSet()
                .add(
                        key,
                        authorities.toArray(new String[0])
                );

        redisTemplate.expire(key, CACHE_TTL);
    }

    public void evictAuthorities(UUID userId) {

        redisTemplate.delete(buildKey(userId));
    }

    private String buildKey(UUID userId) {

        return KEY_PREFIX + userId;
    }

}
