package uet.hungnh.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import uet.hungnh.security.constants.SecurityConstant;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends AbstractAuthenticationFilter {

    private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        Optional<String> token = Optional.ofNullable(httpRequest.getHeader(SecurityConstant.TOKEN_HEADER));

        try {
            if (token.isPresent()) {
                logger.debug("Trying to authenticate user by Token {} ", token);
                processTokenAuthentication(token.get());
            }

            logger.debug("TokenAuthenticationFilter is passing request down the filter chain");
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
        PreAuthenticatedAuthenticationToken authenticationRequest = new PreAuthenticatedAuthenticationToken(token, null);
        Authentication authenticationResult = attemptAuthentication(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
    }
}
