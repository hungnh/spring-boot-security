package uet.hungnh.template.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.template.model.User;
import uet.hungnh.template.repo.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        User user = new User();
        user.setFirstName("User 1");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("user1"));
        user.setEmail("user1@test.com");
        user.setUsername("user1@test.com");
        userRepository.save(user);

        alreadySetup = true;
    }
}