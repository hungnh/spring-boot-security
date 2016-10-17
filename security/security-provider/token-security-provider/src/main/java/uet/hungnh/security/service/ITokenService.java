package uet.hungnh.security.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;

public interface ITokenService {
    @CachePut("token-map")
    Authentication store(String token);

    @Cacheable("token-map")
    Authentication retrieve(String token);

    void remove(String token);
}
