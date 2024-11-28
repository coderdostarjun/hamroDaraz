package com.hamroDaraz.daraz.scheduler;



import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.entity.VerificationToken;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<VerificationToken> expiredTokens = verificationTokenRepository.findByExpiryDateBefore(now);

        for (VerificationToken token : expiredTokens) {

            User user = token.getUser();

            // Delete only the token if the user is already enabled
            if (user.isEnabled()) {
                verificationTokenRepository.delete(token);
            } else {
                // If the user is not enabled, delete both the token and the user
                verificationTokenRepository.delete(token);
                userRepository.delete(user);
            }
//
//            verificationTokenRepository.delete(token);
//           User user = token.getUser();
//            userRepository.delete(user);
        }
    }
}