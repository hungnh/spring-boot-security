package uet.hungnh.security.service;

import uet.hungnh.dto.TokenDTO;
import uet.hungnh.dto.UserDTO;
import uet.hungnh.exception.ServiceException;

import javax.servlet.ServletException;

public interface IRegistrationService {
    TokenDTO register(UserDTO userDTO) throws ServiceException, ServletException;
}
