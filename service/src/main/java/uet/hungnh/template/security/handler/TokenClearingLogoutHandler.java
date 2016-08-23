package uet.hungnh.template.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import uet.hungnh.template.security.service.ITokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static uet.hungnh.template.security.constants.SecurityConstants.TOKEN_HEADER;

public class TokenClearingLogoutHandler implements LogoutHandler {

    @Autowired
    private ITokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String token = request.getHeader(TOKEN_HEADER);
        if (tokenService.contains(token)) {
            tokenService.remove(token);
        }
    }
}
