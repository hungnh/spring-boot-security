package uet.hungnh.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.security.context.ISecurityContextFacade;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.service.ILoginService;
import uet.hungnh.security.service.ITokenService;

import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService implements ILoginService {

    @Autowired
    private ITokenService tokenService;

    @Override
    public TokenDTO login() {
        String authToken = UUID.randomUUID().toString();
        tokenService.store(authToken);
        return new TokenDTO(authToken);
    }
}
