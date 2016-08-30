package uet.hungnh.security.exception;

import org.springframework.http.HttpStatus;
import uet.hungnh.common.exception.GenericException;

public class TokenValidationException extends GenericException {

    private static final long serialVersionUID = -2967435758704765282L;

    public TokenValidationException(String message) {
        super(message, HttpStatus.FORBIDDEN.value());
    }
}
