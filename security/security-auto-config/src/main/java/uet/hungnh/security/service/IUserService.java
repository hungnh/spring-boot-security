package uet.hungnh.security.service;

import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.common.exception.ServiceException;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.model.entity.User;

import javax.servlet.ServletException;

public interface IUserService {
    TokenDTO register(UserDTO userDTO) throws ServiceException, ServletException;

    TokenDTO authenticate();

    UserDTO retrieve();

    String createVerificationTokenForUser(User user);

    GenericResponse validateVerificationToken(String token);
}
