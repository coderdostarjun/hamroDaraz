package com.hamroDaraz.daraz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllUserDetailsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String role;

}
