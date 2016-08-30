package uet.hungnh.security.service;

import freemarker.template.TemplateException;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.security.dto.PasswordDTO;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.exception.EmailExistedException;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.exception.UserNotFoundException;
import uet.hungnh.security.model.entity.User;

import javax.servlet.ServletException;
import java.io.IOException;

public interface IUserService {
    TokenDTO register(UserDTO userDTO) throws ServletException, EmailExistedException;

    TokenDTO login();

    UserDTO retrieve();

    String createVerificationTokenForUser(User user);

    GenericResponse validateVerificationToken(String token) throws TokenValidationException;

    GenericResponse requestResetPassword(String email) throws UserNotFoundException, IOException, TemplateException;

    GenericResponse resetPassword(String token, PasswordDTO passwordDTO) throws TokenValidationException;
}
