package uet.hungnh.security.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import uet.hungnh.security.auth.AuthenticationWithToken;

public interface ITokenService {

    @CachePut("token-map")
    AuthenticationWithToken store(String token);

    @Cacheable("token-map")
    AuthenticationWithToken retrieve(String token);

    void remove(String token);
}
