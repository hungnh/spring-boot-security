package uet.hungnh.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import uet.hungnh.security.service.ITokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static uet.hungnh.security.constants.SecurityConstant.TOKEN_AUTH_HEADER;

public class TokenClearingLogoutHandler implements LogoutHandler {

    @Autowired
    private ITokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String token = request.getHeader(TOKEN_AUTH_HEADER);
        tokenService.remove(token);
    }
}
