package uet.hungnh.template.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.template.dto.TokenDTO;
import uet.hungnh.template.security.service.IAuthenticationService;

import static uet.hungnh.template.security.constants.SecurityConstants.AUTHENTICATION_ENDPOINT;

@RestController
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping(value = AUTHENTICATION_ENDPOINT)
    public TokenDTO authenticate() {
        return authenticationService.authenticate();
    }
}
