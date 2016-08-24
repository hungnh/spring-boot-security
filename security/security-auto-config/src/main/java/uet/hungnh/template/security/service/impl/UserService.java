package uet.hungnh.template.security.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.template.dto.UserDTO;
import uet.hungnh.template.model.entity.User;
import uet.hungnh.template.model.repo.UserRepository;
import uet.hungnh.template.security.service.IUserService;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapperFacade mapper;

    @Override
    public UserDTO retrieve() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return mapper.map(user, UserDTO.class);
    }
}
