package uet.hungnh.template.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uet.hungnh.template.dto.ExceptionDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({ServiceException.class})
    public ExceptionDTO serviceException(ServiceException ex,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setType(ex.getClass().getCanonicalName());
        exceptionDTO.setMessage(ex.getMessage());
        response.setStatus(ex.getHttpStatusCode());
        return exceptionDTO;
    }
}
