package uet.hungnh.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import uet.hungnh.security.auth.AuthenticationWithToken;
import uet.hungnh.security.service.ITokenService;

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

        Authentication preAuthenticatedToken = tokenService.retrieve(token);
        if (preAuthenticatedToken == null) {
            throw new BadCredentialsException("Token is invalid or expired!");
        }

        return new AuthenticationWithToken(
                preAuthenticatedToken.getPrincipal(),
                preAuthenticatedToken.getCredentials(),
                preAuthenticatedToken.getAuthorities(),
                token
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationWithToken.class);
    }
}
