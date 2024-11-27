package com.hamroDaraz.daraz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdTime;
    private boolean isEnabled=false;

    //user table mapped vako xa hai vanew batauxa
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)//shop class le relationship maintain gareko xa user lai mapped garera vanew bujnaana
    private Shop shop;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private VerificationToken verificationToken;


}
