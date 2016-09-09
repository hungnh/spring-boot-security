package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.hungnh.common.dto.GenericResponse;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.exception.EmailExistedException;
import uet.hungnh.security.exception.TokenValidationException;
import uet.hungnh.security.service.ILoginService;
import uet.hungnh.security.service.IRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static uet.hungnh.security.constants.SecurityConstant.EMAIL_CONFIRMATION_ENDPOINT;
import static uet.hungnh.security.constants.SecurityConstant.REGISTRATION_ENDPOINT;

@RestController
public class RegistrationController {

    @Autowired
    private IRegisterService registerService;
    @Autowired
    private ILoginService loginService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = REGISTRATION_ENDPOINT)
    public TokenDTO registration(@RequestBody @Valid UserDTO userDTO)
            throws ServletException, EmailExistedException {
        registerService.register(userDTO);
        request.login(userDTO.getEmail(), userDTO.getPassword());
        TokenDTO responseToken = loginService.login();
        return responseToken;
    }

    @GetMapping(value = EMAIL_CONFIRMATION_ENDPOINT)
    public GenericResponse confirmRegistration(@RequestParam("token") String token)
            throws TokenValidationException {
        return registerService.validateVerificationToken(token);
    }
}
