package uet.hungnh.template.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.template.dto.TokenDTO;
import uet.hungnh.template.security.service.IAuthenticationService;
import uet.hungnh.template.security.service.ITokenService;

@Service
@Transactional
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private ITokenService tokenService;

    @Override
    public TokenDTO authenticate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authToken = tokenService.generateNewToken();
        tokenService.store(authToken, auth);
        return new TokenDTO(authToken);
    }
}
