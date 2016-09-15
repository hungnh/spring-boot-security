package uet.hungnh.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.repo.UserRepository;
import uet.hungnh.security.service.LoginAttemptService;

import javax.servlet.http.HttpServletRequest;

import static uet.hungnh.security.constants.SecurityConstant.FORWARDED_FOR_HEADER;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username : " + username);
        }

        return new CustomUserDetails(user);
    }

    private String getClientIp() {
        final String xfHeader = request.getHeader(FORWARDED_FOR_HEADER);
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
