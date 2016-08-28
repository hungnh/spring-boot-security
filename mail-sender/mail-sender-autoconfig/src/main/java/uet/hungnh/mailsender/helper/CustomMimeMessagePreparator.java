package uet.hungnh.mailsender.helper;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import uet.hungnh.mailsender.dto.AttachmentDTO;
import uet.hungnh.mailsender.dto.ContentDTO;
import uet.hungnh.mailsender.dto.EmailParamsDTO;
import uet.hungnh.mailsender.dto.RecipientDTO;
import uet.hungnh.mailsender.sender.Sender;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.stream.Collectors;

public class CustomMimeMessagePreparator implements MimeMessagePreparator {
    private EmailParamsDTO emailParams;
    private Sender sender;

    public CustomMimeMessagePreparator(EmailParamsDTO emailParams, Sender sender) {
        this.emailParams = emailParams;
        this.sender = sender;
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setSubject(emailParams.getSubject());
        mimeMessageHelper.setFrom(new InternetAddress(sender.getAddress(), sender.getName()));
        addRecipients(mimeMessageHelper);
        addAttachments(mimeMessageHelper);
        addContent(mimeMessageHelper);
    }

    private void addContent(MimeMessageHelper mimeMessageHelper) throws MessagingException {
        ContentDTO emailContent = emailParams.getContentDTO();
        String plainText = emailContent.getPlainText() != null ? emailContent.getPlainText() : "";
        String htmlText = emailContent.getHtmlText() != null ? emailContent.getHtmlText() : plainText;
        mimeMessageHelper.setText(plainText, htmlText);
    }

    private void addAttachments(MimeMessageHelper mimeMessageHelper) throws MessagingException {
        for (AttachmentDTO attachment : emailParams.getAttachmentDTOList()) {
            DataSource dataSource = new ByteArrayDataSource(attachment.getData(), attachment.getType().getValue());
            mimeMessageHelper.addAttachment(attachment.getName(), dataSource);
        }
    }

    private void addRecipients(MimeMessageHelper mimeMessageHelper) throws MessagingException {
        mimeMessageHelper.setTo(
                emailParams.getRecipientDTOList().stream()
                        .filter(recipient -> recipient.getRecipientType().toString().equals(Message.RecipientType.TO.toString()))
                        .map(RecipientDTO::getEmailAddress)
                        .collect(Collectors.toList()).stream().toArray(String[]::new)
        );

        mimeMessageHelper.setCc(
                emailParams.getRecipientDTOList().stream()
                        .filter(recipient -> recipient.getRecipientType().toString().equals(Message.RecipientType.CC.toString()))
                        .map(RecipientDTO::getEmailAddress)
                        .collect(Collectors.toList()).stream().toArray(String[]::new)
        );

        mimeMessageHelper.setBcc(
                emailParams.getRecipientDTOList().stream()
                        .filter(recipient -> recipient.getRecipientType().toString().equals(Message.RecipientType.BCC.toString()))
                        .map(RecipientDTO::getEmailAddress)
                        .collect(Collectors.toList()).stream().toArray(String[]::new)
        );
    }
}
