package uet.hungnh.mailsender.dto;

public class AttachmentDTO {
    private byte[] data;
    private MIMEType type;
    private String name;

    public AttachmentDTO() {
    }

    public AttachmentDTO(byte[] data, MIMEType type, String name) {
        this.data = data;
        this.type = type;
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public MIMEType getType() {
        return type;
    }

    public void setType(MIMEType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum MIMEType {
        MS_EXCEL("application/excel"),
        PDF("application/pdf"),
        ICS("text/calendar");

        private String value;

        MIMEType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
