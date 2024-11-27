package com.hamroDaraz.daraz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {
    private String token;
    private String message;
    private Long  userId;
    private String userName;
}
