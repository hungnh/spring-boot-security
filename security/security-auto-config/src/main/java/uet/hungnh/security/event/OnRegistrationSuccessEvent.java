package uet.hungnh.security.event;

import org.springframework.context.ApplicationEvent;
import uet.hungnh.security.model.entity.User;

public class OnRegistrationSuccessEvent extends ApplicationEvent {

    private final String appUrl;
    private final User user;

    public OnRegistrationSuccessEvent(final String appUrl, final User user) {
        super(user);
        this.appUrl = appUrl;
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public User getUser() {
        return user;
    }
}
