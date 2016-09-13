package uet.hungnh.security.filter;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import uet.hungnh.security.auth.JwtAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

import static uet.hungnh.security.constants.SecurityConstant.JWT_AUTH_HEADER;
import static uet.hungnh.security.constants.SecurityConstant.JWT_AUTH_HEADER_PREFIX;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/**");
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String jwtAuthHeader = request.getHeader(JWT_AUTH_HEADER);
        return jwtAuthHeader != null && jwtAuthHeader.startsWith(JWT_AUTH_HEADER_PREFIX);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String jwtAuthHeader = request.getHeader(JWT_AUTH_HEADER);
        String token = jwtAuthHeader.substring(JWT_AUTH_HEADER_PREFIX.length());

        try {
            JWT jwt = JWTParser.parse(token);
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(jwt);
            return getAuthenticationManager().authenticate(authRequest);
        } catch (ParseException e) {
            throw new BadCredentialsException("Invalid token!");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LOGGER.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        SecurityContextHolder.getContext().setAuthentication(authResult);

        LOGGER.debug("JwtAuthenticationFilter is passing request down the filter chain");
        chain.doFilter(request, response);
    }
}
