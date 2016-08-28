package uet.hungnh.mailsender.service;

import uet.hungnh.dto.EmailParamsDTO;

public interface IMailSender {
    void send(EmailParamsDTO emailParamsDTO);
}
