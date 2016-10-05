package uet.hungnh.oauth2.facebook.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uet.hungnh.oauth2.common.service.IAccessTokenService;
import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.dto.AccessTokenValidationResultDTO;
import uet.hungnh.oauth2.dto.OAuthUserDTO;
import uet.hungnh.oauth2.enums.OAuthProvider;
import uet.hungnh.oauth2.enums.TokenValidationStatus;
import uet.hungnh.oauth2.facebook.api.FacebookAccessToken;
import uet.hungnh.oauth2.facebook.api.FacebookUser;
import uet.hungnh.oauth2.facebook.constant.FacebookAPIConstant;
import uet.hungnh.oauth2.model.entity.OAuthAccessToken;
import uet.hungnh.oauth2.model.entity.OAuthUser;
import uet.hungnh.oauth2.model.repo.OAuthAccessTokenRepository;
import uet.hungnh.oauth2.model.repo.OAuthUserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class FacebookAccessTokenService implements IAccessTokenService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    @Value("${social.facebook.app-id}")
    private String appId;

    @Value("${social.facebook.app-secret}")
    private String appSecret;

    @Override
    public AccessTokenValidationResultDTO exchange(AccessTokenDTO shortLivedToken) {

        FacebookAccessToken longLivedToken = exchangeForLongLivedToken(shortLivedToken);
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

        AccessTokenDTO responseToken = new AccessTokenDTO();
        responseToken.setToken(longLivedToken.getAccessToken());
        responseToken.setUser(mapper.map(oAuthUser, OAuthUserDTO.class));

        AccessTokenValidationResultDTO validationResult = new AccessTokenValidationResultDTO();
        validationResult.setAccessToken(responseToken);
        validationResult.setValidationStatus(TokenValidationStatus.VALID);
        return validationResult;
    }

    private FacebookUser fetchUserProfile(FacebookAccessToken longLivedToken) {

        Map<String, String> params = new HashMap<>();
        params.put("fields", "id,name,email,link,picture{url}");
        params.put("access_token", longLivedToken.getAccessToken());

        return restTemplate.getForObject(
                FacebookAPIConstant.FB_USER_PROFILE_URL_TEMPLATE,
                FacebookUser.class,
                params);
    }

    private FacebookAccessToken exchangeForLongLivedToken(AccessTokenDTO shortLivedToken) {

        Map<String, String> params = new HashMap<>();
        params.put("client_id", appId);
        params.put("client_secret", appSecret);
        params.put("fb_exchange_token", shortLivedToken.getToken());

        return restTemplate.getForObject(
                FacebookAPIConstant.FB_EXCHANGE_TOKEN_URL_TEMPLATE,
                FacebookAccessToken.class,
                params);
    }
}
