package uet.hungnh.security.context;

import org.springframework.security.core.Authentication;
import uet.hungnh.security.userdetails.CustomUserDetails;

public interface ISecurityContextFacade {
    Authentication getAuthentication();
    Object getPrincipal();
    CustomUserDetails getUserDetails();
}
