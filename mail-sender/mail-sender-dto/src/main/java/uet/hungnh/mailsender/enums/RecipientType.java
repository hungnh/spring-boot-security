package uet.hungnh.mailsender.enums;

public enum RecipientType {
    TO("To"),
    CC("Cc"),
    BCC("Bcc");

    private String name;

    RecipientType() {
    }

    RecipientType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}