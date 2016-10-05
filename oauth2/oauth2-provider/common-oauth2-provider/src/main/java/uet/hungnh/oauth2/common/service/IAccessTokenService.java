package uet.hungnh.oauth2.common.service;

import com.fasterxml.jackson.core.JsonParseException;
import uet.hungnh.oauth2.dto.AccessTokenDTO;
import uet.hungnh.oauth2.dto.AccessTokenValidationResultDTO;

import java.io.IOException;

public interface IAccessTokenService {
    AccessTokenValidationResultDTO exchange(AccessTokenDTO accessToken) throws IOException;
}
