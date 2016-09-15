package uet.hungnh.security.service;

import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.exception.EmailExistedException;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.model.entity.User;

import javax.servlet.ServletException;

public interface IRegisterService {
    void register(UserDTO userDTO) throws ServletException, EmailExistedException;

    String createVerificationTokenForUser(User user);

    GenericResponse validateVerificationToken(String token) throws TokenValidationException;
}
