package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.dto.TokenDTO;
import uet.hungnh.security.constants.SecurityConstants;
import uet.hungnh.security.service.IAuthenticationService;

@RestController
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping(value = SecurityConstants.AUTHENTICATION_ENDPOINT)
    public TokenDTO authenticate() {
        return authenticationService.authenticate();
    }
}
