package uet.hungnh.template.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.security.service.IUserService;

import static uet.hungnh.template.security.constants.SecurityConstants.ROLE_USER;

@RestController
@RequestMapping(value = "/user")
@Secured(ROLE_USER)
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public UserDTO retrieve() {
        return userService.retrieve();
    }
}
