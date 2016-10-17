package uet.hungnh.security.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import uet.hungnh.security.auth.AuthenticationWithToken;
import uet.hungnh.security.context.ISecurityContextFacade;
import uet.hungnh.security.service.ITokenService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InMemoryTokenService implements ITokenService {

    @Autowired
    private ISecurityContextFacade securityContext;

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
                        } else {
                            throw new BadCredentialsException("Token is invalid or expired!");
                        }
                    }
                });
    }

    @Override
    public Authentication store(String token) {
        Authentication authentication = securityContext.getAuthentication();
        authTokenCache.put(token, authentication);
        return authentication;
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
