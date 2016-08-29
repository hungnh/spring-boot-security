package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.common.exception.ServiceException;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.service.IUserService;

import javax.servlet.ServletException;

import static uet.hungnh.security.constants.SecurityConstant.REGISTRATION_ENDPOINT;

@RestController
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = REGISTRATION_ENDPOINT)
    public TokenDTO register(@RequestBody UserDTO userDTO) throws ServiceException, ServletException {
        return userService.register(userDTO);
    }
}
