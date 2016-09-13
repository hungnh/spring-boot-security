package uet.hungnh.security.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.security.constants.SecurityConstant;
import uet.hungnh.security.context.ISecurityContextFacade;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.service.ILoginService;
import uet.hungnh.security.userdetails.CustomUserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService implements ILoginService {

    @Autowired
    private ISecurityContextFacade securityContext;

    @Autowired
    private JWSSigner jwsSigner;

    @Override
    public TokenDTO login() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) securityContext.getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Date now = DateTime.now().toDate();
        Date expirationTime = DateTime.now().plusHours(1).toDate();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(now)
                .issuer("hungnh.uet")
                .expirationTime(expirationTime)
                .claim("roles", AuthorityUtils.authorityListToSet(user.getAuthorities()))
                .build();

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        SignedJWT signedJWT = new SignedJWT(jwsHeader, claims);
        try {
            signedJWT.sign(jwsSigner);
            String responseToken = SecurityConstant.JWT_AUTH_HEADER_PREFIX + signedJWT.serialize();
            return new TokenDTO(responseToken);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
