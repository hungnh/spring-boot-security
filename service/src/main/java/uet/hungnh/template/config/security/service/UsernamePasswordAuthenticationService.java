package uet.hungnh.template.config.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import uet.hungnh.template.config.security.auth.AuthenticationToken;
import uet.hungnh.template.model.UserAccount;
import uet.hungnh.template.repo.UserAccountRepository;

import static uet.hungnh.template.config.security.constants.SecurityConstants.ROLE_USER;

public class UsernamePasswordAuthenticationService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public AuthenticationToken authenticate(String username, String password) {

        UserAccount userAccount = userAccountRepository.findByUsername(username);

        if (userAccount == null) {
            throw new BadCredentialsException("User not found!");
        }

        if (!userAccount.getPassword().equals(password)) {
            throw new BadCredentialsException("Wrong password");
        }

        AuthenticationToken authenticationToken
                = new AuthenticationToken(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(ROLE_USER));
        authenticationToken.setAuthenticated(true);

        return authenticationToken;
    }
}
