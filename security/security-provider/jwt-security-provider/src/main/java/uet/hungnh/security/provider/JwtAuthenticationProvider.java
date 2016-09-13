package uet.hungnh.security.provider;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import uet.hungnh.security.auth.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Date;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.claim.issuer}")
    private String jwtIssuer;

    private JWSVerifier jwsVerifier;

    public JwtAuthenticationProvider(JWSVerifier jwsVerifier) {
        this.jwsVerifier = jwsVerifier;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
        JWT jwt = authToken.getJwt();

        if (jwt instanceof PlainJWT) {
            throw new UnsupportedOperationException("Unsecured plain tokens are not supported");
        }
        else if (jwt instanceof EncryptedJWT) {
            throw new UnsupportedOperationException("Unsupported token type");
        }
        else if (jwt instanceof SignedJWT) {
            handleSignedToken((SignedJWT) jwt);
        }

        JWTClaimsSet claims = authToken.getClaims();

        Date now = Date.from(Instant.now());
        Date expirationTime = claims.getExpirationTime();
        if (expirationTime == null || expirationTime.before(now)) {
            throw new BadCredentialsException("Token is expired");
        }

        String issuer = claims.getIssuer();
        if (issuer == null || !issuer.equals(jwtIssuer)) {
            throw new BadCredentialsException("Token's issuer is invalid");
        }

        authToken.setAuthenticated(true);

        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

    private void handleSignedToken(SignedJWT jwt) {
        try {
            if (!jwt.verify(jwsVerifier)) {
                throw new BadCredentialsException("Token signature validation failed");
            }
        } catch (JOSEException e) {
            throw new BadCredentialsException("Token signature validation failed");
        }
    }
}
