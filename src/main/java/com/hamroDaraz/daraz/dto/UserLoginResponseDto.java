package com.hamroDaraz.daraz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {
    private Long  userId;
    private String firstName;
    private String lastName;
    private String token;
    private String message;
    private String roll;
    private String mobileNumber;
    private String email;
}
