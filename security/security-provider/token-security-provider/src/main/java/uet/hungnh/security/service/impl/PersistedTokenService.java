package uet.hungnh.security.service.impl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.security.auth.AuthenticationWithToken;
import uet.hungnh.security.context.ISecurityContextFacade;
import uet.hungnh.security.model.entity.AuthToken;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.repo.AuthTokenRepository;
import uet.hungnh.security.service.ITokenService;
import uet.hungnh.security.userdetails.CustomUserDetails;

public class PersistedTokenService implements ITokenService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PersistedTokenService.class);

    @Value("${token.expired-duration-in-hours}")
    private int tokenExpiredDurationInHours;

    @Autowired
    private ISecurityContextFacade securityContext;
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public AuthenticationWithToken store(String token) {
        LOGGER.warn("Store token: {}", token);
        Authentication authentication = securityContext.getAuthentication();
        AuthToken authToken = persistToken(token, authentication);

        return new AuthenticationWithToken(
                authentication.getPrincipal(),
                authentication.getAuthorities(),
                authToken.getToken(),
                authToken.getExpiredDate());
    }

    private AuthToken persistToken(String token, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        AuthToken authToken = new AuthToken();
        authToken.setUser(user);
        authToken.setToken(token);
        authToken.setExpiredDate(DateTime.now().plusHours(tokenExpiredDurationInHours).toDate());
        authToken = authTokenRepository.save(authToken);
        return authToken;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public AuthenticationWithToken retrieve(String token) {
        LOGGER.warn("Retrieving token: {}", token);
        AuthToken authToken = authTokenRepository.findOne(token);
        if (authToken == null) {
            return null;
        }

        CustomUserDetails userDetails = new CustomUserDetails(authToken.getUser());
        return new AuthenticationWithToken(
                userDetails,
                userDetails.getAuthorities(),
                authToken.getToken(),
                authToken.getExpiredDate());
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void remove(String token) {
        authTokenRepository.delete(token);
    }
}
