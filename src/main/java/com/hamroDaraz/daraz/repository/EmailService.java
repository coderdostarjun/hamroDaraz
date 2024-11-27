package com.hamroDaraz.daraz.repository;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendVerificationEmail(String email,String otp);
}
