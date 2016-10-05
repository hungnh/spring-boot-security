package uet.hungnh.oauth2.google.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleAccessTokenInfo {
    @JsonProperty(value = "aud")
    private String appId;

    @JsonProperty(value = "sub")
    private String userId;

    @JsonProperty(value = "expires_in")
    private int expiresIn;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
