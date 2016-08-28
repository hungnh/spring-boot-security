package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.dto.UserDTO;
import uet.hungnh.security.constants.SecurityConstants;
import uet.hungnh.security.service.IUserService;

@RestController
@RequestMapping(value = "/user")
@Secured(SecurityConstants.ROLE_USER)
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public UserDTO retrieve() {
        return userService.retrieve();
    }
}
