package uet.hungnh.oauth2.facebook.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacebookAccessToken {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private int expiresIn; // The number of seconds until this access token expires.

    @JsonProperty(value = "token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
