package uet.hungnh.security.service;

import uet.hungnh.security.dto.TokenDTO;

public interface IAuthenticationService {
    TokenDTO authenticate();
}
