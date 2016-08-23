package uet.hungnh.template.security.service;

import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.exception.ServiceException;

public interface IRegistrationService {
    UserDTO register(UserDTO userDTO) throws ServiceException;
}
