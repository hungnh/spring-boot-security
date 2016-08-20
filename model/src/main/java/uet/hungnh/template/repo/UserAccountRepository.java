package uet.hungnh.template.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.template.model.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
}
