package uet.hungnh.template.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlingController {

//    @ExceptionHandler({ServiceException.class})
//    public ExceptionDTO serviceException(ServiceException ex,
//                                         HttpServletRequest request,
//                                         HttpServletResponse response) {
//        ExceptionDTO exceptionDTO = new ExceptionDTO();
//        exceptionDTO.setType(ex.getClass().getCanonicalName());
//        exceptionDTO.setMessage(ex.getMessage());
//        response.setStatus(ex.getHttpStatusCode());
//        return exceptionDTO;
//    }
}
