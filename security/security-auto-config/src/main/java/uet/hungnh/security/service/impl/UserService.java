package uet.hungnh.security.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.mailsender.dto.ContentDTO;
import uet.hungnh.mailsender.dto.EmailParamsDTO;
import uet.hungnh.mailsender.dto.RecipientDTO;
import uet.hungnh.mailsender.enums.RecipientType;
import uet.hungnh.mailsender.service.IMailSender;
import uet.hungnh.security.dto.PasswordDTO;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.event.RegistrationSuccessEvent;
import uet.hungnh.security.exception.EmailExistedException;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.exception.UserNotFoundException;
import uet.hungnh.security.model.entity.PasswordResetToken;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.entity.VerificationToken;
import uet.hungnh.security.model.repo.PasswordResetTokenRepository;
import uet.hungnh.security.model.repo.UserRepository;
import uet.hungnh.security.model.repo.VerificationTokenRepository;
import uet.hungnh.security.service.ITokenService;
import uet.hungnh.security.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static uet.hungnh.security.constants.SecurityConstant.RESET_PASSWORD_ENDPOINT;

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
    private Configuration freemarkerConfiguration;
    @Autowired
    private IMailSender mailSender;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public TokenDTO register(UserDTO userDTO) throws ServletException, EmailExistedException {

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

        request.login(user.getUsername(), rawPassword);
        TokenDTO responseToken = login();

        eventPublisher.publishEvent(new RegistrationSuccessEvent(getAppUrl(), user));

        return responseToken;
    }

    @Override
    public TokenDTO login() {
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

    @Override
    public GenericResponse requestResetPassword(String email) throws UserNotFoundException, IOException, TemplateException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("This email doesn't match with any account in the system");
        }

        String resetPasswordToken = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(resetPasswordToken, user);
        passwordResetTokenRepository.save(passwordResetToken);
        sendResetPasswordTokenToUser(user, passwordResetToken);

        return new GenericResponse("success");
    }

    @Override
    public GenericResponse resetPassword(String token, PasswordDTO passwordDTO) throws TokenValidationException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            throw new TokenValidationException("Password reset token is invalid");
        }

        Date now = Date.from(Instant.now());
        if (passwordResetToken.getExpiredDate().before(now)) {
            throw new TokenValidationException("Password reset token is expired!");
        }

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);

        return new GenericResponse("success");
    }

    private void sendResetPasswordTokenToUser(User user, PasswordResetToken passwordResetToken) throws IOException, TemplateException {
        EmailParamsDTO resetPasswordEmail = new EmailParamsDTO();
        resetPasswordEmail.setSubject("Somebody requested a new password for your account");

        RecipientDTO recipient = new RecipientDTO(RecipientType.TO, user.getEmail());
        resetPasswordEmail.getRecipients().add(recipient);

        Template fmTemplate = freemarkerConfiguration.getTemplate("email-template/reset-password-token.ftl");

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("user", user);

        String verificationLink = String.format("%s%s?token=%s", getAppUrl(), RESET_PASSWORD_ENDPOINT, passwordResetToken.getToken());
        templateModel.put("verificationLink", verificationLink);

        ContentDTO content = new ContentDTO();
        content.setHtmlText(FreeMarkerTemplateUtils.processTemplateIntoString(fmTemplate, templateModel));

        resetPasswordEmail.setContent(content);

        mailSender.send(resetPasswordEmail);
    }

    private String getAppUrl() {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private boolean emailExisted(String email) {
        return userRepository.countByEmail(email) > 0;
    }

}
