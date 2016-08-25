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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        String email = "user1@test.com";

        if (alreadySetup || userRepository.findByUsername(email) != null) {
            return;
        }

        User user = new User();
        user.setFirstName("User 1");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("user1"));
        user.setEmail(email);
        user.setUsername(email);
        userRepository.save(user);

        alreadySetup = true;
    }
}