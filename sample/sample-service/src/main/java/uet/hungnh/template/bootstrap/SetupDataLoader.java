package uet.hungnh.template.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.template.model.entity.User;
import uet.hungnh.template.model.repo.UserRepository;

@Component
@Profile("DEV")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private static final String EMAIL = "user@test.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup || alreadyExisted()) {
            return;
        }

        User user = new User();
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("user@123"));
        user.setEmail(EMAIL);
        user.setUsername(EMAIL);
        userRepository.save(user);

        alreadySetup = true;
    }

    private boolean alreadyExisted() {
        return userRepository.findByUsername(EMAIL) != null;
    }
}