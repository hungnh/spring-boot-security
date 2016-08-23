package uet.hungnh.template.security.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import uet.hungnh.template.security.service.LoginAttemptService;

@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authFailureEvent) {
        WebAuthenticationDetails authDetails = (WebAuthenticationDetails) authFailureEvent.getAuthentication();
        if (authDetails != null) {
            loginAttemptService.loginFailed(authDetails.getRemoteAddress());
        }
    }
}
