package com.hamroDaraz.daraz.repository;



import com.hamroDaraz.daraz.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String otp);

    List<VerificationToken> findByExpiryDateBefore(LocalDateTime now);
}
