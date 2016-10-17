package uet.hungnh.security.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.security.model.entity.AuthToken;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
}
