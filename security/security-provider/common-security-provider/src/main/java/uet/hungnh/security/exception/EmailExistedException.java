package uet.hungnh.security.exception;

import org.springframework.http.HttpStatus;
import uet.hungnh.common.exception.GenericException;

public class EmailExistedException extends GenericException {

    private static final long serialVersionUID = 2349307736404492204L;

    public EmailExistedException(String message) {
        super(message, HttpStatus.CONFLICT.value());
    }
}
