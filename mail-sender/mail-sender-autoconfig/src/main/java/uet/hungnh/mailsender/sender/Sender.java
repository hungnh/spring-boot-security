package uet.hungnh.mailsender.sender;

public class Sender {

    private String address;
    private String name;

    public Sender() {
    }

    public Sender(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
