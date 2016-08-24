package uet.hungnh.template.security.service;

import org.springframework.security.core.Authentication;

public interface ITokenService {
    void evictExpiredTokens();

    String generateNewToken();

    void store(String token, Authentication authentication);

    boolean contains(String token);

    Authentication retrieve(String token);

    void remove(String token);
}
