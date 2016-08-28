package uet.hungnh.mailsender.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailParamsDTO {
    ContentDTO contentDTO;
    private String subject;
    private List<RecipientDTO> recipientDTOList = new ArrayList<>();
    private List<AttachmentDTO> attachmentDTOList = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<RecipientDTO> getRecipientDTOList() {
        return recipientDTOList;
    }

    public void setRecipientDTOList(List<RecipientDTO> recipientDTOList) {
        this.recipientDTOList = recipientDTOList;
    }

    public ContentDTO getContentDTO() {
        return contentDTO;
    }

    public void setContentDTO(ContentDTO contentDTO) {
        this.contentDTO = contentDTO;
    }

    public List<AttachmentDTO> getAttachmentDTOList() {
        return attachmentDTOList;
    }

    public void setAttachmentDTOList(List<AttachmentDTO> attachmentDTOList) {
        this.attachmentDTOList = attachmentDTOList;
    }
}


