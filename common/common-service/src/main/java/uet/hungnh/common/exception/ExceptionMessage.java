package uet.hungnh.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
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
