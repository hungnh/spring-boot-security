package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.service.IUserService;

import static uet.hungnh.security.constants.SecurityConstant.LOGIN_ENDPOINT;

@RestController
public class LoginController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = LOGIN_ENDPOINT)
    public TokenDTO authenticate() {
        return userService.authenticate();
    }
}
