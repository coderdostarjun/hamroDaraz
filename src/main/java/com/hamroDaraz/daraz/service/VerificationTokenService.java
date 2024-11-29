package com.hamroDaraz.daraz.service;



import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.entity.VerificationToken;
import org.springframework.stereotype.Service;

@Service
public interface VerificationTokenService {
    VerificationToken createVerificationToken(User user, String otp);
    VerificationToken getToken(String otp);
    void deleteToken(Long id);

}
