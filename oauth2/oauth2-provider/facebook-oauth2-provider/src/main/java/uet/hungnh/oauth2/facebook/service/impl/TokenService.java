package uet.hungnh.oauth2.facebook.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uet.hungnh.oauth2.dto.OAuthUserDTO;
import uet.hungnh.oauth2.enums.OAuthProvider;
import uet.hungnh.oauth2.facebook.api.FacebookUser;
import uet.hungnh.oauth2.facebook.constant.OAuth2Constant;
import uet.hungnh.oauth2.dto.OAuthAccessTokenDTO;
import uet.hungnh.oauth2.facebook.api.FacebookAccessToken;
import uet.hungnh.oauth2.facebook.service.ITokenService;
import uet.hungnh.oauth2.model.entity.OAuthAccessToken;
import uet.hungnh.oauth2.model.entity.OAuthUser;
import uet.hungnh.oauth2.model.repo.OAuthAccessTokenRepository;
import uet.hungnh.oauth2.model.repo.OAuthUserRepository;


import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenService implements ITokenService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    @Override
    public OAuthAccessTokenDTO exchangeForLongLivedToken(OAuthAccessTokenDTO shortLivedToken) {

        FacebookAccessToken longLivedToken = exchange(shortLivedToken);
        FacebookUser facebookUser = fetchUserProfile(longLivedToken);

        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setProviderUserId(facebookUser.getId());
        oAuthUser.setProvider(OAuthProvider.FACEBOOK);
        oAuthUser.setName(facebookUser.getName());
        oAuthUser.setEmail(facebookUser.getEmail());
        oAuthUser.setProfileUrl(facebookUser.getLink());
        oAuthUser.setAvatarUrl(facebookUser.getPicture().getData().getUrl());
        oAuthUserRepository.save(oAuthUser);

        OAuthAccessToken accessToken = new OAuthAccessToken();
        accessToken.setAccessToken(longLivedToken.getAccessToken());
        Date expiredDate = DateTime.now().plusSeconds(longLivedToken.getExpiresIn()).toDate();
        accessToken.setExpiredDate(expiredDate);
        accessToken.setUser(oAuthUser);
        oAuthAccessTokenRepository.save(accessToken);

        OAuthAccessTokenDTO responseToken = new OAuthAccessTokenDTO();
        responseToken.setToken(longLivedToken.getAccessToken());
        responseToken.setUser(mapper.map(oAuthUser, OAuthUserDTO.class));
        return responseToken;
    }

    private FacebookUser fetchUserProfile(FacebookAccessToken longLivedToken) {
        Map<String, String> params = new HashMap<>();
        params.put("fields", "id,name,email,link,picture{url}");
        params.put("access_token", longLivedToken.getAccessToken());

        return restTemplate.getForObject(
                OAuth2Constant.FB_USER_PROFILE_URL_TEMPLATE,
                FacebookUser.class,
                params);
    }

    private FacebookAccessToken exchange(OAuthAccessTokenDTO shortLivedToken) {

        Map<String, String> params = new HashMap<>();
        params.put("client_id", "1113089908714345");
        params.put("client_secret", "b09f2c05e1f720dd2f56cddf7765aa2d");
        params.put("fb_exchange_token", shortLivedToken.getToken());

        return restTemplate.getForObject(
                OAuth2Constant.FB_EXCHANGE_TOKEN_URL_TEMPLATE,
                FacebookAccessToken.class,
                params);
    }
}
