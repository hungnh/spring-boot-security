package uet.hungnh.template.security.service;

import org.springframework.security.core.Authentication;

public interface ITokenService {
    String generateNewToken();

    void store(String token, Authentication authentication);

    Authentication retrieve(String token);

    void remove(String token);
}
