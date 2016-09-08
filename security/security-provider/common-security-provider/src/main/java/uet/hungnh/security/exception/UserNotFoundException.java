package uet.hungnh.security.exception;

import org.springframework.http.HttpStatus;
import uet.hungnh.common.exception.GenericException;

public class UserNotFoundException extends GenericException {

    private static final long serialVersionUID = -814407768617242134L;

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
