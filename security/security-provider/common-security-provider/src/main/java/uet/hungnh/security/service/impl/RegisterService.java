package uet.hungnh.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.common.service.IAppMetadataFacade;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.event.RegistrationSuccessEvent;
import uet.hungnh.security.exception.EmailExistedException;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.entity.VerificationToken;
import uet.hungnh.security.model.repo.UserRepository;
import uet.hungnh.security.model.repo.VerificationTokenRepository;
import uet.hungnh.security.service.IRegisterService;

import javax.servlet.ServletException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterService implements IRegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IAppMetadataFacade appMetadata;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    @Override
    public void register(UserDTO userDTO) throws ServletException, EmailExistedException {

        if (emailExisted(userDTO.getEmail())) {
            throw new EmailExistedException("There is already an email with that email address");
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

        eventPublisher.publishEvent(new RegistrationSuccessEvent(appMetadata.getAppUrl(), user));
    }

    private boolean emailExisted(String email) {
        return userRepository.countByEmail(email) > 0;
    }

    @Override
    public String createVerificationTokenForUser(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken.getToken();
    }

    @Override
    public GenericResponse validateVerificationToken(String token) throws TokenValidationException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new TokenValidationException("Verification token is invalid");
        }

        Date now = Date.from(Instant.now());
        if (verificationToken.getExpiredDate().before(now)) {
            throw new TokenValidationException("Verification token is expired");
        }

        verificationTokenRepository.delete(verificationToken);

        return new GenericResponse("success");
    }

}
