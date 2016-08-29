package uet.hungnh.security.model.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uet.hungnh.security.model.entity.VerificationToken;

import java.util.Date;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, UUID> {
    VerificationToken findByToken(String token);

    @Modifying
    @Query("delete from VerificationToken t where t.expiredDate <= :date ")
    void deleteAllExpiredSince(@Param("date") Date date);
}
