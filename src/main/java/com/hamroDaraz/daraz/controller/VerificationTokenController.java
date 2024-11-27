package com.hamroDaraz.daraz.controller;



import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.entity.VerificationToken;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/token")
public class VerificationTokenController {
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp) {
        VerificationToken verificationToken = verificationTokenService.getToken(otp);

        if (verificationToken == null) {
            return "Invalid or expired OTP";
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        verificationTokenService.deleteToken(verificationToken);

        return "OTP verified successfully. Your account is now active.";
    }
    }
