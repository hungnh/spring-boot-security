package uet.hungnh.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import uet.hungnh.security.model.entity.User;

import java.util.Collection;

import static uet.hungnh.security.constants.SecurityConstant.ROLE_ADMIN;
import static uet.hungnh.security.constants.SecurityConstant.ROLE_USER;

public class CustomUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = 3123972151983305430L;

    public CustomUserDetails() {
    }

    public CustomUserDetails(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setUsername(user.getUsername());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPassword(user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(ROLE_USER, ROLE_ADMIN);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
