package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.common.exception.ServiceException;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.service.IUserService;

import javax.servlet.ServletException;

import static uet.hungnh.security.constants.SecurityConstant.EMAIL_CONFIRMATION_ENDPOINT;
import static uet.hungnh.security.constants.SecurityConstant.REGISTRATION_ENDPOINT;

@RestController
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = REGISTRATION_ENDPOINT)
    public TokenDTO registration(@RequestBody UserDTO userDTO) throws ServiceException, ServletException {
        return userService.register(userDTO);
    }

    @GetMapping(value = EMAIL_CONFIRMATION_ENDPOINT)
    public GenericResponse confirmRegistration(@RequestParam("token") String token) throws ServiceException {
        return userService.validateVerificationToken(token);
    }
}
