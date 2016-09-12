package uet.hungnh.security.context;

import org.springframework.security.core.Authentication;

public interface ISecurityContextFacade {
    Authentication getAuthentication();
    Object getPrincipal();
}
