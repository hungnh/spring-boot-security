package uet.hungnh.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import uet.hungnh.template.mailsender.sender.Sender;

@Configuration
@ComponentScan(basePackages = "uet.hungnh.template.mailsender")
@PropertySource("classpath:/mail-sender.properties")
public class MailSenderConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Sender sender() {
        Sender sender = new Sender();
        sender.setAddress(environment.getProperty("mail.sender.address"));
        sender.setName(environment.getProperty("mail.sender.display-name"));
        return sender;
    }
}
