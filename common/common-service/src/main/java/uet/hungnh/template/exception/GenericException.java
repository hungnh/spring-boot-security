package uet.hungnh.template.exception;

public abstract class GenericException extends Exception {

    private String message;
    private int httpStatusCode;

    public GenericException() {
    }

    public GenericException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.message = super.getMessage();
        this.httpStatusCode = exceptionMessage.getHttpStatus().value();
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
