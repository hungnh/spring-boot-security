package uet.hungnh.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uet.hungnh.security.userdetails.CustomUserDetails;

@Component
public class SecurityContextFacade implements ISecurityContextFacade {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Object getPrincipal() {
        return getAuthentication().getPrincipal();
    }

    @Override
    public CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getPrincipal();
    }
}
