package uet.hungnh.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(AbstractAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    protected AbstractAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    protected Authentication attemptAuthentication(Authentication authenticationRequest) {
        Authentication authResult = authenticationManager.authenticate(authenticationRequest);
        if (authResult == null || !authResult.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate user for provided credentials!");
        }

        logger.debug("User is successfully authenticated!");

        return authResult;
    }

    protected HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    protected HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }
}
