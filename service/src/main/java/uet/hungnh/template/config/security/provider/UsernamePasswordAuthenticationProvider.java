package uet.hungnh.template.config.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import uet.hungnh.template.config.security.auth.AuthenticationToken;
import uet.hungnh.template.config.security.service.TokenService;
import uet.hungnh.template.config.security.service.UsernamePasswordAuthenticationService;

import java.util.Optional;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;
    private UsernamePasswordAuthenticationService usernamePasswordAuthenticationService;

    public UsernamePasswordAuthenticationProvider(TokenService tokenService,
                                                  UsernamePasswordAuthenticationService usernamePasswordAuthenticationService) {
        this.tokenService = tokenService;
        this.usernamePasswordAuthenticationService = usernamePasswordAuthenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Optional<String> username = (Optional) authentication.getPrincipal();
        Optional<String> password = (Optional) authentication.getCredentials();

        if (!username.isPresent() || !password.isPresent()) {
            throw new BadCredentialsException("Invalid user credentials!");
        }

        AuthenticationToken authenticationResult = usernamePasswordAuthenticationService.authenticate(username.get(), password.get());
        String newToken = tokenService.generateNewToken();
        authenticationResult.setToken(newToken);
        tokenService.store(newToken, authenticationResult);

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
