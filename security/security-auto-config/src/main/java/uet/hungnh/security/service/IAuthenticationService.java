package uet.hungnh.security.service;

import uet.hungnh.dto.TokenDTO;

public interface IAuthenticationService {
    TokenDTO authenticate();
}
