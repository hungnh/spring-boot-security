package uet.hungnh.template.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import uet.hungnh.template.security.service.TokenService;

import java.util.Optional;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Optional<String> token = (Optional) authentication.getPrincipal();

        if (!token.isPresent() || token.get().isEmpty()) {
            throw new BadCredentialsException("Invalid token!");
        }

        if (!tokenService.contains(token.get())) {
            throw new BadCredentialsException("Token is invalid or expired!");
        }

        return tokenService.retrieve(token.get());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
