package uet.hungnh.mailsender.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailParamsDTO {

    private ContentDTO content;
    private String subject;
    private List<RecipientDTO> recipients = new ArrayList<>();
    private List<AttachmentDTO> attachments = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<RecipientDTO> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<RecipientDTO> recipients) {
        this.recipients = recipients;
    }

    public ContentDTO getContent() {
        return content;
    }

    public void setContent(ContentDTO content) {
        this.content = content;
    }

    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }
}


