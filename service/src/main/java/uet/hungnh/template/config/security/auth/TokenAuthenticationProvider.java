package uet.hungnh.template.config.security.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import uet.hungnh.template.config.security.token.TokenService;

import java.util.Optional;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;

    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional token = Optional.ofNullable(authentication.getPrincipal());
        if (!token.isPresent() || token.get().toString().isEmpty()) {
            throw new BadCredentialsException("Invalid token!");
        }
        if (!tokenService.contains(token.get().toString())) {
            throw new BadCredentialsException("Token is invalid or expired!");
        }
        return tokenService.retrieve(token.get().toString());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
