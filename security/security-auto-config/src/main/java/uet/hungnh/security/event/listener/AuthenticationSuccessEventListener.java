package uet.hungnh.security.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import uet.hungnh.security.service.LoginAttemptService;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authSuccessEvent) {
        WebAuthenticationDetails authDetails = (WebAuthenticationDetails) authSuccessEvent.getAuthentication().getDetails();
        if (authDetails != null) {
            loginAttemptService.loginSucceeded(authDetails.getRemoteAddress());
        }
    }
}
