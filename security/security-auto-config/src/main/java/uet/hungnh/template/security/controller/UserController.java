package uet.hungnh.template.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.security.service.IUserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public UserDTO retrieve() {
        return userService.retrieve();
    }
}
