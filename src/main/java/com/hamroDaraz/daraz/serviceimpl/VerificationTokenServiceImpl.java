package com.hamroDaraz.daraz.serviceimpl;



import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.entity.VerificationToken;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.repository.VerificationTokenRepository;
import com.hamroDaraz.daraz.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    private static final int OTP_EXPIRATION_MINUTES = 5;
    @Override
    public VerificationToken createVerificationToken(User user, String otp) {
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setToken(otp);
        token.setCreatedDate(LocalDateTime.now());
        token.setExpiryDate(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
        return verificationTokenRepository.save(token);
    }
//token vanekai otp ho yeha don't confuse
    @Override
    public VerificationToken getToken(String otp) {
        VerificationToken token = verificationTokenRepository.findByToken(otp);
        if (token != null && isTokenExpired(token)) {
            deleteToken(token);
            return null;
        }
        return token;
    }
    boolean isTokenExpired(VerificationToken token) {
        return LocalDateTime.now().isAfter(token.getCreatedDate().plusMinutes(OTP_EXPIRATION_MINUTES));
    }

    @Override
    public void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }


}
