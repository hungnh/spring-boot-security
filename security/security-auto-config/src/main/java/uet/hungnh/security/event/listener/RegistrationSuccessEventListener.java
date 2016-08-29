package uet.hungnh.security.event.listener;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uet.hungnh.common.event.AsyncListener;
import uet.hungnh.mailsender.dto.ContentDTO;
import uet.hungnh.mailsender.dto.EmailParamsDTO;
import uet.hungnh.mailsender.dto.RecipientDTO;
import uet.hungnh.mailsender.enums.RecipientType;
import uet.hungnh.mailsender.service.IMailSender;
import uet.hungnh.security.event.OnRegistrationSuccessEvent;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.service.IUserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uet.hungnh.security.constants.SecurityConstant.EMAIL_CONFIRMATION_ENDPOINT;

@AsyncListener
public class RegistrationSuccessEventListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    private final Logger logger = LoggerFactory.getLogger(RegistrationSuccessEventListener.class);

    @Autowired
    private Configuration freemarkerConfiguration;
    @Autowired
    private IMailSender mailSender;
    @Autowired
    private IUserService userService;

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        sendConfirmationEmail(event.getUser(), event.getAppUrl());
    }

    private void sendConfirmationEmail(User user, String appUrl) {

        try {
            EmailParamsDTO confirmationEmail = new EmailParamsDTO();
            confirmationEmail.setSubject("Please confirm your registration");

            RecipientDTO recipient = new RecipientDTO(RecipientType.TO, user.getEmail());
            confirmationEmail.getRecipients().add(recipient);

            Template fmTemplate = freemarkerConfiguration.getTemplate("email-template/welcome-user.ftl");

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("user", user);

            String verificationToken = userService.createVerificationTokenForUser(user);
            String verificationLink = String.format("%s%s?token=%s", appUrl, EMAIL_CONFIRMATION_ENDPOINT, verificationToken);
            templateModel.put("verificationLink", verificationLink);

            ContentDTO content = new ContentDTO();
            content.setHtmlText(FreeMarkerTemplateUtils.processTemplateIntoString(fmTemplate, templateModel));

            confirmationEmail.setContent(content);

            mailSender.send(confirmationEmail);
        } catch (IOException | TemplateException e) {
            logger.warn("Unable to send confirmation email to user : {}", user.getEmail());
        }
    }
}
