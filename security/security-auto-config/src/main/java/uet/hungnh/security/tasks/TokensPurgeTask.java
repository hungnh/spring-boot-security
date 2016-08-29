package uet.hungnh.security.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.security.model.repo.VerificationTokenRepository;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = false)
public class TokensPurgeTask {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Scheduled(cron = "${token.purge.cron.expression}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        verificationTokenRepository.deleteAllExpiredSince(now);
    }
}
