package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.dto.TokenDTO;
import uet.hungnh.dto.UserDTO;
import uet.hungnh.exception.ServiceException;
import uet.hungnh.security.constants.SecurityConstants;
import uet.hungnh.security.service.IRegistrationService;

import javax.servlet.ServletException;

@RestController
public class RegistrationController {

    @Autowired
    private IRegistrationService registrationService;

    @PostMapping(value = SecurityConstants.REGISTER_ENDPOINT)
    public TokenDTO register(@RequestBody UserDTO userDTO) throws ServiceException, ServletException {
        return registrationService.register(userDTO);
    }
}
