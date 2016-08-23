package uet.hungnh.template.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.template.model.UserAccount;
import uet.hungnh.template.repo.UserAccountRepository;

import javax.servlet.http.HttpServletRequest;

import static uet.hungnh.template.security.constants.SecurityConstants.FORWARDED_FOR_HEADER;
import static uet.hungnh.template.security.constants.SecurityConstants.ROLE_USER;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final String ip = getClientIp();
        if (loginAttemptService.isBlocked(ip)) {
            throw new LockedException("Your IP is blocked due to maximum login attempts is exceeded!");
        }

        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username : " + username);
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(ROLE_USER)
        );
    }

    private String getClientIp() {
        final String xfHeader = request.getHeader(FORWARDED_FOR_HEADER);
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}