package uet.hungnh.security.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.common.exception.ExceptionMessage;
import uet.hungnh.common.exception.ServiceException;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.event.OnRegistrationSuccessEvent;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.entity.VerificationToken;
import uet.hungnh.security.model.repo.UserRepository;
import uet.hungnh.security.model.repo.VerificationTokenRepository;
import uet.hungnh.security.service.ITokenService;
import uet.hungnh.security.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements IUserService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public TokenDTO register(UserDTO userDTO) throws ServiceException, ServletException {

        if (emailExisted(userDTO.getEmail())) {
            throw new ServiceException(ExceptionMessage.EMAIL_EXISTED);
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getEmail());

        String rawPassword = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);

        request.login(user.getUsername(), rawPassword);
        TokenDTO responseToken = authenticate();

        eventPublisher.publishEvent(new OnRegistrationSuccessEvent(getAppUrl(), user));

        return responseToken;
    }

    @Override
    public TokenDTO authenticate() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authToken = tokenService.generateNewToken();
        auth.setDetails(authToken);
        tokenService.store(authToken, auth);
        return new TokenDTO(authToken);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO retrieve() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public String createVerificationTokenForUser(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken.getToken();
    }

    private String getAppUrl() {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private boolean emailExisted(String email) {
        return userRepository.countByEmail(email) > 0;
    }

}
