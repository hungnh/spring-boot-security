package uet.hungnh.oauth2.dto;

import uet.hungnh.oauth2.enums.TokenValidationStatus;

public class AccessTokenValidationResultDTO {
    private TokenValidationStatus validationStatus;
    private AccessTokenDTO accessToken;

    public TokenValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(TokenValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public AccessTokenDTO getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessTokenDTO accessToken) {
        this.accessToken = accessToken;
    }
}
