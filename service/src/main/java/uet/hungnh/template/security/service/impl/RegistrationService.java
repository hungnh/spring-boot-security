package uet.hungnh.template.security.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.exception.ExceptionMessage;
import uet.hungnh.template.exception.ServiceException;
import uet.hungnh.template.model.User;
import uet.hungnh.template.repo.UserRepository;
import uet.hungnh.template.security.service.IRegistrationService;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationService implements IRegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperFacade mapper;

    @Override
    public UserDTO register(UserDTO userDTO) throws ServiceException {
        if(emailExisted(userDTO.getEmail())) {
            throw new ServiceException(ExceptionMessage.EMAIL_EXISTED);
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getEmail());

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return mapper.map(user, UserDTO.class);
    }

    private boolean emailExisted(String email) {
        return userRepository.countByEmail(email) > 0;
    }
}
