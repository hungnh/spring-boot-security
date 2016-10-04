package uet.hungnh.oauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import uet.hungnh.oauth2.constant.OAuth2Constant;
import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.dto.FacebookAccessTokenDTO;
import uet.hungnh.oauth2.service.ITokenService;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenService implements ITokenService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AccessTokenDTO exchangeForLongLivedToken(AccessTokenDTO shortLivedToken) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "1113089908714345");
        params.put("client_secret", "b09f2c05e1f720dd2f56cddf7765aa2d");
        params.put("fb_exchange_token", shortLivedToken.getToken());
        FacebookAccessTokenDTO facebookAccessTokenDTO = restTemplate.getForObject(OAuth2Constant.FB_EXCHANGE_TOKEN_URL_TEMPLATE, FacebookAccessTokenDTO.class, params);
        return new AccessTokenDTO(facebookAccessTokenDTO.getAccessToken());
    }
}
