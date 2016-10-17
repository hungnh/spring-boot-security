package uet.hungnh.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import uet.hungnh.security.auth.AuthenticationWithToken;
import uet.hungnh.security.service.ITokenService;

import java.time.Instant;
import java.util.Date;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private ITokenService tokenService;

    public TokenAuthenticationProvider(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AuthenticationWithToken authToken = (AuthenticationWithToken) authentication;
        String token = authToken.getToken();

        if (token.isEmpty()) {
            throw new BadCredentialsException("Invalid token!");
        }

        AuthenticationWithToken authenticationWithToken = tokenService.retrieve(token);
        if (authenticationWithToken == null) {
            throw new BadCredentialsException("Token is invalid or expired!");
        }

        Date now = Date.from(Instant.now());
        Date expiredDate = authenticationWithToken.getExpiredDate();
        if (expiredDate.before(now)) {
            throw new BadCredentialsException("Token is expired!");
        }

        return authenticationWithToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationWithToken.class);
    }
}
