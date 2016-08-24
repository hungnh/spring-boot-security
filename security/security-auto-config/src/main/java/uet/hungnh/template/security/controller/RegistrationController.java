package uet.hungnh.template.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.template.dto.TokenDTO;
import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.exception.ServiceException;
import uet.hungnh.template.security.service.IRegistrationService;

import javax.servlet.ServletException;

import static uet.hungnh.template.security.constants.SecurityConstants.REGISTER_ENDPOINT;

@RestController
public class RegistrationController {

    @Autowired
    private IRegistrationService registrationService;

    @PostMapping(value = REGISTER_ENDPOINT)
    public TokenDTO register(@RequestBody UserDTO userDTO) throws ServiceException, ServletException {
        return registrationService.register(userDTO);
    }
}
