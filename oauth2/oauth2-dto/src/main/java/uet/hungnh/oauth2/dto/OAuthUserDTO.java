package uet.hungnh.oauth2.dto;

import uet.hungnh.oauth2.enums.OAuthProvider;

public class OAuthUserDTO {
    private String providerUserId;
    private String name;
    private String email;
    private OAuthProvider provider;

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OAuthProvider getProvider() {
        return provider;
    }

    public void setProvider(OAuthProvider provider) {
        this.provider = provider;
    }
}
