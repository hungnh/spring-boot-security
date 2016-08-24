package uet.hungnh.template.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authToken = tokenService.generateNewToken();
        auth.setDetails(authToken);
        tokenService.store(authToken, auth);
        return new TokenDTO(authToken);
    }
}
