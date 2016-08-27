package uet.hungnh.template.mailsender.service;

import uet.hungnh.template.dto.EmailParamsDTO;

public interface IMailSender {
    void send(EmailParamsDTO emailParamsDTO);
}
