package com.hamroDaraz.daraz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminLoginRequest {
    private String email;
    private String password;
}
