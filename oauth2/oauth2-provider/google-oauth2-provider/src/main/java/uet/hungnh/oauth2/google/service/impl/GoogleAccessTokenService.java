package uet.hungnh.oauth2.google.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import uet.hungnh.oauth2.google.api.GoogleAccessTokenInfo;
import uet.hungnh.oauth2.google.api.GoogleUserProfile;
import uet.hungnh.oauth2.google.constant.GoogleAPIConstant;
import uet.hungnh.oauth2.model.entity.OAuthAccessToken;
import uet.hungnh.oauth2.model.entity.OAuthUser;
import uet.hungnh.oauth2.model.repo.OAuthAccessTokenRepository;
import uet.hungnh.oauth2.model.repo.OAuthUserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoogleAccessTokenService implements IAccessTokenService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private ObjectMapper jacksonObjectMapper;
    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    @Value("${social.google.app-id}")
    private String appId;

    @Value("${social.google.app-secret}")
    private String appSecret;

    @Override
    public AccessTokenValidationResultDTO exchange(AccessTokenDTO accessToken) throws IOException {

        AccessTokenValidationResultDTO validationResultDTO = new AccessTokenValidationResultDTO();

        String validationResponse = validateAccessToken(accessToken);
        if (validationResponse.contains("error_description")) {
            validationResultDTO.setValidationStatus(TokenValidationStatus.INVALID);
            return validationResultDTO;
        }

        GoogleAccessTokenInfo accessTokenInfo = jacksonObjectMapper.readValue(validationResponse, GoogleAccessTokenInfo.class);
        if (!accessTokenInfo.getAppId().equals(appId)) {
            validationResultDTO.setValidationStatus(TokenValidationStatus.INVALID);
            return validationResultDTO;
        }

        GoogleUserProfile userProfile = fetchUserProfile(accessToken, accessTokenInfo);

        OAuthUser userEntity = new OAuthUser();
        userEntity.setProviderUserId(userProfile.getId());
        userEntity.setProvider(OAuthProvider.GOOGLE);
        userEntity.setName(userProfile.getDisplayName());
        userEntity.setEmail(userProfile.getEmail());
        userEntity.setProfileUrl(userProfile.getUrl());
        userEntity.setAvatarUrl(userProfile.getImage());
        oAuthUserRepository.save(userEntity);

        OAuthAccessToken tokenEntity = new OAuthAccessToken();
        tokenEntity.setAccessToken(accessToken.getToken());
        Date expiredDate = DateTime.now().plusSeconds(accessTokenInfo.getExpiresIn()).toDate();
        tokenEntity.setExpiredDate(expiredDate);
        tokenEntity.setUser(userEntity);
        oAuthAccessTokenRepository.save(tokenEntity);

        AccessTokenDTO responseToken = new AccessTokenDTO();
        responseToken.setToken(accessToken.getToken());
        responseToken.setUser(mapper.map(userEntity, OAuthUserDTO.class));

        AccessTokenValidationResultDTO validationResult = new AccessTokenValidationResultDTO();
        validationResult.setAccessToken(responseToken);
        validationResult.setValidationStatus(TokenValidationStatus.VALID);

        return validationResult;
    }

    private GoogleUserProfile fetchUserProfile(AccessTokenDTO accessToken, GoogleAccessTokenInfo accessTokenInfo) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("user_id", accessTokenInfo.getUserId());
        params.put("access_token", accessToken.getToken());
        params.put("fields", "displayName,emails/value,id,image/url,url");

        String userProfileJson = restTemplate.getForObject(
                GoogleAPIConstant.GOOGLE_USER_PROFILE_URL_TEMPLATE,
                String.class,
                params);

        JsonNode root = jacksonObjectMapper.readTree(userProfileJson);

        GoogleUserProfile userProfile = new GoogleUserProfile();
        userProfile.setId(root.get("id").asText());
        userProfile.setDisplayName(root.get("displayName").asText());
        userProfile.setUrl(root.get("url").asText());
        userProfile.setImage(root.get("image").get("url").asText());
        userProfile.setEmail(root.get("emails").get(0).get("value").asText());

        return userProfile;
    }

    private String validateAccessToken(AccessTokenDTO accessToken) {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken.getToken());

        return restTemplate.getForObject(
                GoogleAPIConstant.GOOGLE_TOKEN_INFO_URL_TEMPLATE,
                String.class,
                params);
    }
}
