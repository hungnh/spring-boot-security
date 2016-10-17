package uet.hungnh.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;
import java.util.Date;

public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {

    private static final long serialVersionUID = -5603030876641663394L;

    private String token;
    private Date expiredDate;

    public AuthenticationWithToken(String token) {
        super(null, null);
        this.token = token;
    }

    public AuthenticationWithToken(Object aPrincipal,
                                   Collection<? extends GrantedAuthority> anAuthorities,
                                   String token, Date expiredDate) {
        super(aPrincipal, null, anAuthorities);
        this.token = token;
        this.expiredDate = expiredDate;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }
}
