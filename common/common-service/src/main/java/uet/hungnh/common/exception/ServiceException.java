package uet.hungnh.common.exception;

public class ServiceException extends GenericException {

    private static final long serialVersionUID = 8782800153291867954L;

    public ServiceException() {
    }

    public ServiceException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }
}
