package uet.hungnh.oauth2.google.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.dto.AccessTokenValidationResultDTO;
import uet.hungnh.oauth2.google.service.impl.GoogleAccessTokenService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/oauth/google/access-token")
public class GoogleAccessTokenController {
    @Autowired
    private GoogleAccessTokenService tokenService;

    @PostMapping("/exchange")
    public AccessTokenValidationResultDTO exchange(@RequestBody AccessTokenDTO accessToken) throws IOException {
        return tokenService.exchange(accessToken);
    }
}
