package uet.hungnh.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.service.ITokenService;

@RestController
@RequestMapping(value = "/oauth/access-token")
public class TokenController {

    @Autowired
    private ITokenService tokenService;

    @PostMapping("/exchange")
    public AccessTokenDTO exchangeAccessToken(@RequestBody AccessTokenDTO shortLivedToken) {
        return tokenService.exchangeForLongLivedToken(shortLivedToken);
    }
}
