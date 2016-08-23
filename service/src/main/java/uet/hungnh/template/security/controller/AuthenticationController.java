package uet.hungnh.template.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.template.security.model.TokenResponse;
import uet.hungnh.template.security.service.TokenService;

import static uet.hungnh.template.security.constants.SecurityConstants.AUTHENTICATION_ENDPOINT;

@RestController
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = AUTHENTICATION_ENDPOINT)
    public TokenResponse authenticate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authToken = tokenService.generateNewToken();
        tokenService.store(authToken, auth);
        return new TokenResponse(authToken);
    }
}
