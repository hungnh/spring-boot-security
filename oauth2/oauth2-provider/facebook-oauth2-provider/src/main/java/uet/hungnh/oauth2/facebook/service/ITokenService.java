package uet.hungnh.oauth2.facebook.service;

import uet.hungnh.oauth2.dto.OAuthAccessTokenDTO;

public interface ITokenService {
    OAuthAccessTokenDTO exchangeForLongLivedToken(OAuthAccessTokenDTO shortLivedToken);
}
