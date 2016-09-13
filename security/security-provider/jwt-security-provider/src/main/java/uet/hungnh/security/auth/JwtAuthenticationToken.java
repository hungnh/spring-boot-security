package uet.hungnh.security.auth;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationToken implements Authentication {

    private JWT jwt;
    private JWTClaimsSet claims;
    private final Collection<GrantedAuthority> authorities;
    private boolean authenticated = false;

    public JwtAuthenticationToken(JWT jwt) throws ParseException {
        this.jwt = jwt;
        this.claims = jwt.getJWTClaimsSet();

        List<String> roles = jwt.getJWTClaimsSet().getStringListClaim("roles");
        List<GrantedAuthority> temp = new ArrayList<>();
        if (roles != null) {
            roles.stream().forEach(role -> temp.add(new SimpleGrantedAuthority(role)));
            this.authorities = Collections.unmodifiableList(temp);
        }
        else {
            this.authorities = AuthorityUtils.NO_AUTHORITIES;
        }

        this.authenticated = false;
    }

    public JWT getJwt() {
        return jwt;
    }

    public JWTClaimsSet getClaims() {
        return claims;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return this.claims.toJSONObject();
    }

    @Override
    public Object getPrincipal() {
        return this.claims.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.claims.getSubject();
    }
}
