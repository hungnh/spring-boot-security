package uet.hungnh.oauth2.facebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.oauth2.common.service.IAccessTokenService;
import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.dto.AccessTokenValidationResultDTO;
import uet.hungnh.oauth2.facebook.service.impl.FacebookAccessTokenService;

@RestController
@RequestMapping(value = "/oauth/facebook/access-token")
public class FacebookAccessTokenController {

    @Autowired
    private FacebookAccessTokenService tokenService;

    @PostMapping("/exchange")
    public AccessTokenValidationResultDTO exchange(@RequestBody AccessTokenDTO accessToken) {
        return tokenService.exchange(accessToken);
    }
}
