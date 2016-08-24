package uet.hungnh.template.security.service;

import uet.hungnh.template.dto.TokenDTO;
import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.exception.ServiceException;

import javax.servlet.ServletException;

public interface IRegistrationService {
    TokenDTO register(UserDTO userDTO) throws ServiceException, ServletException;
}
