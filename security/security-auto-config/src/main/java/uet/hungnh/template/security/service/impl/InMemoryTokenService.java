package uet.hungnh.template.security.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import uet.hungnh.template.security.service.ITokenService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InMemoryTokenService implements ITokenService {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryTokenService.class);
    private LoadingCache<String, Authentication> authTokenCache;

    public InMemoryTokenService() {
        super();
        authTokenCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(new CacheLoader<String, Authentication>() {
                    @Override
                    public Authentication load(String key) throws Exception {
                        Authentication auth = retrieve(key);
                        if (auth != null) {
                            return auth;
                        }
                        else {
                            throw new BadCredentialsException("Token is invalid or expired!");
                        }
                    }
                });
    }

    @Override
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void store(String token, Authentication authentication) {
        authTokenCache.put(token, authentication);
    }

    @Override
    public Authentication retrieve(String token) {
        return authTokenCache.getIfPresent(token);
    }

    @Override
    public void remove(String token) {
        authTokenCache.invalidate(token);
    }
}
