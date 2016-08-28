package uet.hungnh.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.common.dto.ExceptionDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
public class ExceptionHandlingController {

    @ExceptionHandler({ServiceException.class})
    public ExceptionDTO serviceException(ServiceException ex,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setException(ex.getClass().getCanonicalName());
        exceptionDTO.setMessage(ex.getMessage());
        response.setStatus(ex.getHttpStatusCode());
        return exceptionDTO;
    }

    @ExceptionHandler({Exception.class})
    public ExceptionDTO anyException(Exception ex,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setException(ex.getClass().getCanonicalName());
        exceptionDTO.setMessage(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return exceptionDTO;
    }
}
