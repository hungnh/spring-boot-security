package uet.hungnh.security.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import uet.hungnh.security.auth.AuthenticationWithToken;
import uet.hungnh.security.context.ISecurityContextFacade;
import uet.hungnh.security.model.entity.AuthToken;
import uet.hungnh.security.service.ITokenService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InMemoryTokenService implements ITokenService {

    @Autowired
    private ISecurityContextFacade securityContext;

    @Value("${token.expired-duration-in-hours}")
    private int tokenExpiredDurationInHours;

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTokenService.class);
    private LoadingCache<String, AuthenticationWithToken> authTokenCache;

    public InMemoryTokenService() {
        super();
        authTokenCache = CacheBuilder.newBuilder()
                .expireAfterAccess(tokenExpiredDurationInHours, TimeUnit.HOURS)
                .build(new CacheLoader<String, AuthenticationWithToken>() {
                    @Override
                    public AuthenticationWithToken load(String key) throws Exception {
                        AuthenticationWithToken auth = retrieve(key);
                        if (auth != null) {
                            return auth;
                        } else {
                            throw new BadCredentialsException("Token is invalid or expired!");
                        }
                    }
                });
    }

    @Override
    public AuthenticationWithToken store(String token) {
        Authentication authentication = securityContext.getAuthentication();
        AuthenticationWithToken authenticationWithToken = new AuthenticationWithToken(
                authentication.getPrincipal(),
                authentication.getAuthorities(),
                token,
                DateTime.now().plusHours(tokenExpiredDurationInHours).toDate());
        authTokenCache.put(token, authenticationWithToken);
        return authenticationWithToken;
    }

    @Override
    public AuthenticationWithToken retrieve(String token) {
        return authTokenCache.getIfPresent(token);
    }

    @Override
    public void remove(String token) {
        authTokenCache.invalidate(token);
    }
}
