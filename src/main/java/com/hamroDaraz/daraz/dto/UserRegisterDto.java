package com.hamroDaraz.daraz.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//i.e UserlaiRegistergarda chayena k k ho tini field haru rakhenxa yeha
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @Column(unique = true)
    private String email;
    private String password;
//    @JsonIgnore
    private String confirmPassword;
}
