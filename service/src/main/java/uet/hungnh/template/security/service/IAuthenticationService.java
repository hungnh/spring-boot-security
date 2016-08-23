package uet.hungnh.template.security.service;

import uet.hungnh.template.dto.TokenDTO;

public interface IAuthenticationService {
    TokenDTO authenticate();
}
