package uet.hungnh.mailsender.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uet.hungnh.mailsender.dto.EmailParamsDTO;
import uet.hungnh.mailsender.helper.CustomMimeMessagePreparator;
import uet.hungnh.mailsender.sender.Sender;
import uet.hungnh.mailsender.service.IMailSender;

/**
 * Created by Admin on 12/25/2015.
 */
@Service
public class MailSenderService implements IMailSender {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Sender sender;

    @Override
    public void send(EmailParamsDTO emailParamsDTO) {
        CustomMimeMessagePreparator customMimeMessagePreparator = new CustomMimeMessagePreparator(emailParamsDTO, sender);
        javaMailSender.send(customMimeMessagePreparator);
    }
}
