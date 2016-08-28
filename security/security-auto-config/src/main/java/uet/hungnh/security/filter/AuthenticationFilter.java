package uet.hungnh.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;
import uet.hungnh.security.constants.SecurityConstants;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        Optional<String> username = Optional.ofNullable(httpRequest.getHeader(SecurityConstants.USERNAME_HEADER));
        Optional<String> password = Optional.ofNullable(httpRequest.getHeader(SecurityConstants.PASSWORD_HEADER));
        Optional<String> token = Optional.ofNullable(httpRequest.getHeader(SecurityConstants.TOKEN_HEADER));

        String resourcePath = (new UrlPathHelper()).getPathWithinApplication(httpRequest);

        try {
            if (postToAuthenticate(httpRequest, resourcePath)) {
                logger.debug("Trying to authenticate user {} by username/password method ", username);
                processUsernamePasswordAuthentication(httpRequest, httpResponse, username, password);
            }

            if (token.isPresent()) {
                logger.debug("Trying to authenticate user by Token {} ", token);
                processTokenAuthentication(token.get());
            }

            logger.debug("AuthenticationFilter is passing request down the filter chain");
            chain.doFilter(request, response);
        } catch (InternalAuthenticationServiceException e) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private void processTokenAuthentication(String token) {
        Authentication authenticationResult = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        PreAuthenticatedAuthenticationToken authenticationRequest = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(authenticationRequest);
    }

    private void processUsernamePasswordAuthentication(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       Optional<String> username,
                                                       Optional<String> password) throws IOException {
        if (!username.isPresent() || !password.isPresent()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate without username or password!");
        }

        Authentication authResult = tryToAuthenticateWithUsernameAndPassword(username.get(), password.get());
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(authenticationRequest);
    }

    private Authentication tryToAuthenticate(Authentication authenticationRequest) {
        Authentication authResult = authenticationManager.authenticate(authenticationRequest);
        if (authResult == null || !authResult.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate user for provided credentials!");
        }
        logger.debug("User is successfully authenticated!");
        return authResult;
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return (
                "POST".equals(httpRequest.getMethod())
                        && SecurityConstants.AUTHENTICATION_ENDPOINT.equals(resourcePath)
        );
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }


}
