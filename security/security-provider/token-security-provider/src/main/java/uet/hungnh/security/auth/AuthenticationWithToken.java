package uet.hungnh.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {

    private static final long serialVersionUID = -5603030876641663394L;

    private String token;

    public AuthenticationWithToken(String token) {
        super(null, null);
        this.token = token;
    }

    public AuthenticationWithToken(Object aPrincipal, Object aCredentials, String token) {
        super(aPrincipal, aCredentials);
        this.token = token;
    }

    public AuthenticationWithToken(Object aPrincipal, Object aCredentials,
                                   Collection<? extends GrantedAuthority> anAuthorities, String token) {
        super(aPrincipal, aCredentials, anAuthorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
