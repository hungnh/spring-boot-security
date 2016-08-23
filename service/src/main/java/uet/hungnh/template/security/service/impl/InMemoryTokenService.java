package uet.hungnh.template.security.service.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import uet.hungnh.template.security.service.ITokenService;

import java.util.UUID;

public class InMemoryTokenService implements ITokenService {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryTokenService.class);
    private static final Cache authTokenCache = CacheManager.getInstance().getCache("auth-token-cache");
    private static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    @Override
    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        authTokenCache.evictExpiredElements();
    }

    @Override
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void store(String token, Authentication authentication) {
        authTokenCache.put(new Element(token, authentication));
    }

    @Override
    public boolean contains(String token) {
        return authTokenCache.isKeyInCache(token);
    }

    @Override
    public Authentication retrieve(String token) {
        return (Authentication) authTokenCache.get(token).getObjectValue();
    }

    @Override
    public void remove(String token) {
        authTokenCache.remove(token);
    }
}
