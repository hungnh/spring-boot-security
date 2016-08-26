package uet.hungnh.template.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import uet.hungnh.template.security.service.ITokenService;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private ITokenService tokenService;

    public TokenAuthenticationProvider(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();

        if (token.isEmpty()) {
            throw new BadCredentialsException("Invalid token!");
        }

        Authentication authToken = tokenService.retrieve(token);
        if (authToken == null) {
            throw new BadCredentialsException("Token is invalid or expired!");
        }

        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
