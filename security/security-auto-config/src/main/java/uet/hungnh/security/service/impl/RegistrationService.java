package uet.hungnh.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.common.exception.ExceptionMessage;
import uet.hungnh.common.exception.ServiceException;
import uet.hungnh.security.dto.TokenDTO;
import uet.hungnh.security.dto.UserDTO;
import uet.hungnh.security.model.entity.User;
import uet.hungnh.security.model.repo.UserRepository;
import uet.hungnh.security.service.IAuthenticationService;
import uet.hungnh.security.service.IRegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationService implements IRegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TokenDTO register(UserDTO userDTO) throws ServiceException, ServletException {

        if (emailExisted(userDTO.getEmail())) {
            throw new ServiceException(ExceptionMessage.EMAIL_EXISTED);
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getEmail());

        String rawPassword = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);

        request.login(user.getUsername(), rawPassword);

        return authenticationService.authenticate();
    }

    private boolean emailExisted(String email) {
        return userRepository.countByEmail(email) > 0;
    }
}
