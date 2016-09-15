package uet.hungnh.security.service;

import freemarker.template.TemplateException;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.security.dto.PasswordDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.exception.UserNotFoundException;

import java.io.IOException;

public interface IUserService {
    UserDTO retrieve();

    GenericResponse requestResetPassword(String email) throws UserNotFoundException, IOException, TemplateException;

    GenericResponse resetPassword(String token, PasswordDTO passwordDTO) throws TokenValidationException;
}
