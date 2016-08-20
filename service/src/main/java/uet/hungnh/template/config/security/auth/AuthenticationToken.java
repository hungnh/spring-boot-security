package uet.hungnh.template.config.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

public class AuthenticationToken extends PreAuthenticatedAuthenticationToken {

    public AuthenticationToken(Object aPrincipal, Object aCredentials) {
        super(aPrincipal, aCredentials);
    }

    public AuthenticationToken(Object aPrincipal, Object aCredentials,
                               Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public void setToken(String token) {
        setDetails(token);
    }

    public String getToken() {
        return (String) getDetails();
    }
}
