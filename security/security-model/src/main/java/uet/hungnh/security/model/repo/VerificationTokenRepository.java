package uet.hungnh.security.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.security.model.entity.VerificationToken;

import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, UUID> {
}
