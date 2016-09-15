package uet.hungnh.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static uet.hungnh.security.constants.SecurityConstant.*;

public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsernamePasswordAuthenticationFilter.class);

    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(LOGIN_ENDPOINT, "POST"));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        Optional<String> username = Optional.ofNullable(request.getHeader(USERNAME_HEADER));
        Optional<String> password = Optional.ofNullable(request.getHeader(PASSWORD_HEADER));

        if (!username.isPresent() || !password.isPresent()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate without username or password!");
        }

        LOGGER.debug("Trying to authenticate user {} by username/password method ", username.get());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username.get(), password.get());

        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        LOGGER.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        SecurityContextHolder.getContext().setAuthentication(authResult);

        LOGGER.debug("UsernamePasswordAuthenticationFilter is passing request down the filter chain");
        chain.doFilter(request, response);
    }
}
