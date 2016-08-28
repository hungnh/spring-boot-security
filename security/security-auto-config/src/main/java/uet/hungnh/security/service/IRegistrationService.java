package uet.hungnh.security.service;

import uet.hungnh.common.exception.ServiceException;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;

import javax.servlet.ServletException;

public interface IRegistrationService {
    TokenDTO register(UserDTO userDTO) throws ServiceException, ServletException;
}
