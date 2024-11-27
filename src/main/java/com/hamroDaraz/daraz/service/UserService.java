package com.hamroDaraz.daraz.service;



import com.hamroDaraz.daraz.dto.GetAllUserDetailsDto;
import com.hamroDaraz.daraz.dto.UserRegisterDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    UserRegisterDto registerUser(UserRegisterDto userDto);

    List<GetAllUserDetailsDto> getAllUserDetails();

    GetAllUserDetailsDto getUserDetailByToken(String userToken);

    Page<GetAllUserDetailsDto> getUserList(int page, int size);

    String buildUserPageURL(int page, int size);
}
