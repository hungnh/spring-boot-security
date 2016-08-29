package uet.hungnh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.security.constants.SecurityConstant;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.service.IUserService;

@RestController
@Secured(SecurityConstant.ROLE_USER)
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/user")
    public UserDTO retrieve() {
        return userService.retrieve();
    }
}
