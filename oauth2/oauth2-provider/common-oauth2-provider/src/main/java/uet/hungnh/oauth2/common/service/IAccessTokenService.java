package uet.hungnh.oauth2.common.service;

import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.dto.AccessTokenValidationResultDTO;

public interface IAccessTokenService {
    AccessTokenValidationResultDTO validateAccessToken(AccessTokenDTO accessToken);
}
