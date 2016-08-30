package uet.hungnh.security.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.security.model.entity.PasswordResetToken;

import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, UUID> {
    PasswordResetToken findByToken(String token);
}
