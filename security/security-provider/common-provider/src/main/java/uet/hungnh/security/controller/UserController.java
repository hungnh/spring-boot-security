package uet.hungnh.security.controller;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.security.constants.SecurityConstant;
import uet.hungnh.security.dto.PasswordDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.exception.UserNotFoundException;
import uet.hungnh.security.service.IUserService;

import java.io.IOException;

import static uet.hungnh.security.constants.SecurityConstant.REQUEST_RESET_PASSWORD_ENDPOINT;
import static uet.hungnh.security.constants.SecurityConstant.RESET_PASSWORD_ENDPOINT;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/user")
    @Secured(SecurityConstant.ROLE_USER)
    public UserDTO retrieve() {
        return userService.retrieve();
    }

    @PostMapping(value = REQUEST_RESET_PASSWORD_ENDPOINT)
    public GenericResponse requestResetPassword(@RequestParam("email") String email)
            throws UserNotFoundException, TemplateException, IOException {
        return userService.requestResetPassword(email);
    }

    @PostMapping(value = RESET_PASSWORD_ENDPOINT)
    public GenericResponse resetPassword(@RequestParam("token") String token,
                                         @RequestBody PasswordDTO passwordDTO)
            throws TokenValidationException {
        return userService.resetPassword(token, passwordDTO);
    }
}
