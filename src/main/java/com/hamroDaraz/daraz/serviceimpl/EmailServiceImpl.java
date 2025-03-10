package com.hamroDaraz.daraz.serviceimpl;



import com.hamroDaraz.daraz.repository.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendVerificationEmail(String email, String otp) {
        try {
            String subject = "OTP Verification";
            String message = "Your OTP for account verification is: " + otp;

            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo(email);
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            mailSender.send(emailMessage);
            System.out.println("OTP sent successfully to " + email);
        } catch (Exception e) {
            System.out.println("Error sending OTP: " + e.getMessage());
        }

    }
}
