package uet.hungnh.template.dto;

public class RecipientDTO {
    private RecipientType recipientType;
    private String emailAddress;

    public RecipientDTO() {
    }

    public RecipientDTO(RecipientType recipientType, String emailAddress) {
        this.recipientType = recipientType;
        this.emailAddress = emailAddress;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
