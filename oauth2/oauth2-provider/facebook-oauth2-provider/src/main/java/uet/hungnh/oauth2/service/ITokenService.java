package uet.hungnh.oauth2.service;

import uet.hungnh.oauth2.dto.AccessTokenDTO;

public interface ITokenService {
    AccessTokenDTO exchangeForLongLivedToken(AccessTokenDTO shortLivedToken);
}
