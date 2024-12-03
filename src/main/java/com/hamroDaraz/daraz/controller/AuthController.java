package com.hamroDaraz.daraz.controller;



import com.hamroDaraz.daraz.config.JwtTokenHelper;
import com.hamroDaraz.daraz.constants.MessageConstants;
import com.hamroDaraz.daraz.dto.*;
import com.hamroDaraz.daraz.entity.Admin;
import com.hamroDaraz.daraz.entity.Cart;
import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.repository.AdminRepository;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private ModelMapper modelMapper;


    //for-admin
//    @Autowired
//    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    //loggerinfo rakhana hareka class ma i.e good practice
    private static final Logger logInfo = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/user/register")
    public ResponseEntity<?> registerSeller(@RequestBody UserRegisterDto userDto) {
        logInfo.error(userDto.getPassword());
        logInfo.error(userDto.getConfirmPassword());
        List<Map<String, String>> errors = new ArrayList<>();

        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("firstName", MessageConstants.MESSAGE_INVALIDFIRSTNAME);
            errors.add(error);
        }
        if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_INVALIDLASTNAME);
            errors.add(error);
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_EMAIL);
            errors.add(error);
        }
        if (!userDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_INVALIDEMAILFORMAT);
            errors.add(error);
        }

        if (userDto.getMobileNumber() == null || userDto.getMobileNumber().equals("") || userDto.getMobileNumber().length() != 10) {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_INVALIDMOBILENUMBER);
            errors.add(error);
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty() || userDto.getPassword().length() < 8 || userDto.getPassword().length() > 20) {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_INVALIDPASSWORD);
            errors.add(error);
        }

        String password = userDto.getPassword();
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$")) {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_PASSWORD_COMPLEXITY);
            errors.add(error);
        }

        if ((!Objects.equals(userDto.getPassword(), userDto.getConfirmPassword())))
        //2 ta ma juna if use gareni hunxa
//        if(!Objects.equals(userDto.getPassword(), userDto.getConfirmPassword()))
        {
            Map<String, String> error = new HashMap<>();
            error.put("error", MessageConstants.MESSAGE_INVALIDCONFIRMPASSWORD);
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            logInfo.error(errors.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", errors));
        }
        if (userRepository.existsByMobileNumber(userDto.getMobileNumber())) {
            logInfo.error("User with the same Mobile Number already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", "User with the same Mobile Number already exists."));
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            logInfo.error("User with the same Email already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", "User with the same Email already exists."));
        }
//yeha mathi sabai validation ko concept aba chai main concept of register
        try {
            UserRegisterDto registeredUser = userService.registerUser(userDto);
            logInfo.info("User Registration Successful: " + registeredUser.getEmail());
            return ResponseEntity.ok()
                    .body(Map.of("status", "success", "details", registeredUser, "message", "User Registered Successfully"));
            //ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            logInfo.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        logInfo.info("user login API");
        Optional<User> theUser = userRepository.findByEmail(userLoginRequestDto.getEmail());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        if (theUser.isPresent()) {
            User user = theUser.get();

            if(!user.isEnabled())  //user enabled xa xaina check garna ko lagi
            {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                        .body(Map.of("status", "error", "message", "please verify your otp & try again."));
            }
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword())
                );

                if (authentication.isAuthenticated()) {
                    userLoginResponseDto.setToken(jwtTokenHelper.generateToken(user.getEmail(), "user"));
                    userLoginResponseDto.setMessage("Login Successfull");
                    userLoginResponseDto.setUserId(user.getId());
                    userLoginResponseDto.setFirstName(user.getFirstName());
                    userLoginResponseDto.setLastName(user.getLastName());
                    userLoginResponseDto.setRoll(user.getRole());
                    userLoginResponseDto.setEmail(user.getEmail());
                    userLoginResponseDto.setMobileNumber(user.getMobileNumber());
                    return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
                } else {
                    logInfo.error("Invalid email or Password!");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
                }
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
            }
        } else {
            //        logInfo.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "User not found"));
        }
    }


    //for admin

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest adminLoginRequest) {
        logInfo.info("Admin login API");
        logInfo.info(adminLoginRequest.getEmail());
        Optional<Admin> adminUser = adminRepository.findByEmail(adminLoginRequest.getEmail());

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();

        if (adminUser.isPresent()) {
            Admin admin = adminUser.get();
            try {

                Authentication authentication = authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())  //user ko email password pathayera
                        //authenticatie user khokinai vanera authentication manager ko help le garenxa
                );

                if (authentication.isAuthenticated()) {
                    adminLoginResponse.setToken(jwtTokenHelper.generateToken(admin.getEmail(), admin.getRole()));
                    adminLoginResponse.setMessage("Login Successful");
                    adminLoginResponse.setUserId(admin.getId());
                    adminLoginResponse.setUserName(admin.getUserName());
                    return new ResponseEntity<>(adminLoginResponse, HttpStatus.OK);
                } else {
//                logInfo.error("Invalid email or Password!");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
                }
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
            }
        } else {
//        logInfo.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "User not found"));
        }
    }
}

