package uet.hungnh.oauth2.google.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.oauth2.google.service.IGoogleAccessTokenService;

@RestController
@RequestMapping(value = "/oauth/google/access-token")
public class GoogleAccessTokenController {

    @Autowired
    private IGoogleAccessTokenService tokenService;
}
