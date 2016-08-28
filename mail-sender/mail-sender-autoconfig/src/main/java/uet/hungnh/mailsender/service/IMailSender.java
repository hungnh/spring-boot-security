package uet.hungnh.mailsender.service;

import uet.hungnh.mailsender.dto.EmailParamsDTO;

public interface IMailSender {
    void send(EmailParamsDTO emailParamsDTO);
}
