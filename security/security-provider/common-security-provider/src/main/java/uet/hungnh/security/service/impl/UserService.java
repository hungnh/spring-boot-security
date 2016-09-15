package uet.hungnh.security.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.common.service.IAppMetadataFacade;
import uet.hungnh.mailsender.dto.ContentDTO;
import uet.hungnh.mailsender.dto.EmailParamsDTO;
import uet.hungnh.mailsender.dto.RecipientDTO;
import uet.hungnh.mailsender.enums.RecipientType;
import uet.hungnh.mailsender.service.IMailSender;
import uet.hungnh.security.context.ISecurityContextFacade;
import uet.hungnh.security.dto.PasswordDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.exception.UserNotFoundException;
import uet.hungnh.security.model.entity.PasswordResetToken;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.repo.PasswordResetTokenRepository;
import uet.hungnh.security.model.repo.UserRepository;
import uet.hungnh.security.service.IUserService;

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
    private MapperFacade mapper;
    @Autowired
    private Configuration freemarkerConfiguration;
    @Autowired
    private IMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAppMetadataFacade appMetadata;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private ISecurityContextFacade securityContext;

    @Override
    @Transactional(readOnly = true)
    public UserDTO retrieve() {
        Authentication authentication = securityContext.getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return mapper.map(user, UserDTO.class);
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

        String verificationLink = String.format("%s%s?token=%s",
                appMetadata.getAppUrl(),
                RESET_PASSWORD_ENDPOINT,
                passwordResetToken.getToken());
        templateModel.put("verificationLink", verificationLink);

        ContentDTO content = new ContentDTO();
        content.setHtmlText(FreeMarkerTemplateUtils.processTemplateIntoString(fmTemplate, templateModel));

        resetPasswordEmail.setContent(content);

        mailSender.send(resetPasswordEmail);
    }
}
