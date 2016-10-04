package uet.hungnh.oauth2.dto;

public class AccessTokenDTO {
    private String token;

    public AccessTokenDTO() {
    }

    public AccessTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
