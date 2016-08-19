package uet.hungnh.template.config.security.token;

public class TokenResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenResponse() {
    }

    public TokenResponse(String token) {
        this.token = token;
    }
}
