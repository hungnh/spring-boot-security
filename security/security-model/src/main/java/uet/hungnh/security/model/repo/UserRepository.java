package uet.hungnh.security.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.security.model.entity.User;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByUsername(String username);

    int countByEmail(String email);
}
