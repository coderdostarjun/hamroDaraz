package com.hamroDaraz.daraz.scheduler;



import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.entity.VerificationToken;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeleteOTP {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Scheduled(cron = "0 */1 * * * ?")
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<VerificationToken> expiredTokens = verificationTokenRepository.findByExpiryDateBefore(now);

        for (VerificationToken token : expiredTokens) {
            verificationTokenRepository.delete(token);
           User user = token.getUser();
            userRepository.delete(user);
        }
    }
}