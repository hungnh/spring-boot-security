package uet.hungnh.oauth2.google.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uet.hungnh.oauth2.google.service.IGoogleAccessTokenService;
import uet.hungnh.oauth2.model.repo.OAuthAccessTokenRepository;
import uet.hungnh.oauth2.model.repo.OAuthUserRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoogleAccessTokenService implements IGoogleAccessTokenService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;
}
