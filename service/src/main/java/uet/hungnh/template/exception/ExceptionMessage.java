package uet.hungnh.template.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
    EMAIL_EXISTED("There is already an email with that email address", HttpStatus.CONFLICT)
    ;


    private String message;
    private HttpStatus httpStatus;

    ExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
