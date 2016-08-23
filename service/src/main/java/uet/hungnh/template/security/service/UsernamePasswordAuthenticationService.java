package uet.hungnh.template.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import uet.hungnh.template.model.UserAccount;
import uet.hungnh.template.repo.UserAccountRepository;
import uet.hungnh.template.security.model.AuthenticationToken;

import static uet.hungnh.template.security.constants.SecurityConstants.ROLE_USER;

@Service
public class UsernamePasswordAuthenticationService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public AuthenticationToken authenticate(String username, String password) {

        UserAccount user = userAccountRepository.findByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("User not found!");
        }

        if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Wrong password");
        }

        AuthenticationToken authenticationToken
                = new AuthenticationToken(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(ROLE_USER));
        authenticationToken.setAuthenticated(true);

        return authenticationToken;
    }
}
