package uet.hungnh.sample;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uet.hungnh.mailsender.dto.ContentDTO;
import uet.hungnh.mailsender.dto.EmailParamsDTO;
import uet.hungnh.mailsender.dto.RecipientDTO;
import uet.hungnh.mailsender.enums.RecipientType;
import uet.hungnh.mailsender.service.IMailSender;
import uet.hungnh.security.model.entity.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SampleTest extends BaseTest {

    @Autowired
    IMailSender mailSender;
    @Autowired
    Configuration freemarkerConfiguration;

    @Test
    public void test() {
        assertThat(true, is(equalTo(true)));
    }

    @Test
    public void mailSenderTest() throws IOException, TemplateException {
        EmailParamsDTO emailParamsDTO = new EmailParamsDTO();
        emailParamsDTO.setSubject("a subject");

        RecipientDTO recipientDTO = new RecipientDTO(RecipientType.TO, "hungnh@higgsup.com");
        emailParamsDTO.getRecipients().add(recipientDTO);

        ContentDTO contentDTO = new ContentDTO();

        Template fmTemplate = freemarkerConfiguration.getTemplate("welcome-user.ftl");
        Map<String, Object> model = new HashMap<>();
        User user = new User();
        user.setEmail("hungnh@gmail.com");
        model.put("user", user);
        model.put("verificationLink", "http://cashpools.net/");

        contentDTO.setHtmlText(FreeMarkerTemplateUtils.processTemplateIntoString(fmTemplate, model));

        emailParamsDTO.setContent(contentDTO);

        mailSender.send(emailParamsDTO);
    }
}
