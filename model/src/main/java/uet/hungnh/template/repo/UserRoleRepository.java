package uet.hungnh.template.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.template.model.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
