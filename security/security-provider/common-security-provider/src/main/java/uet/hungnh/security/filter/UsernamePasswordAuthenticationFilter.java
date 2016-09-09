package uet.hungnh.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.UrlPathHelper;
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

public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationFilter {

    private final static Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationFilter.class);

    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        Optional<String> username = Optional.ofNullable(httpRequest.getHeader(SecurityConstant.USERNAME_HEADER));
        Optional<String> password = Optional.ofNullable(httpRequest.getHeader(SecurityConstant.PASSWORD_HEADER));

        String resourcePath = (new UrlPathHelper()).getPathWithinApplication(httpRequest);

        try {
            if (postToLogin(httpRequest, resourcePath)) {
                if (!username.isPresent() || !password.isPresent()) {
                    throw new InternalAuthenticationServiceException("Unable to authenticate without username or password!");
                }

                logger.debug("Trying to authenticate user {} by username/password method ", username.get());
                processUsernamePasswordAuthentication(username.get(), password.get());
            }

            logger.debug("UsernamePasswordAuthenticationFilter is passing request down the filter chain");
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

    private void processUsernamePasswordAuthentication(String username, String password) throws IOException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult = attemptAuthentication(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    private boolean postToLogin(HttpServletRequest httpRequest, String resourcePath) {
        return (
                "POST".equals(httpRequest.getMethod())
                        && SecurityConstant.LOGIN_ENDPOINT.equals(resourcePath)
        );
    }
}
