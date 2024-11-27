package com.hamroDaraz.daraz.controller;



import com.hamroDaraz.daraz.dto.GetAllUserDetailsDto;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
   private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private static final Logger logInfo = LoggerFactory.getLogger(AuthController.class);
    //getAll User Details
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUserDetails()
    {
         List<GetAllUserDetailsDto> getAllUserDetailsDtoList=userService.getAllUserDetails();
         return ResponseEntity.ok(getAllUserDetailsDtoList);
    }
//    getAll User Details by using pagination

    @GetMapping("/userList")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        logInfo.info("Get list of users API hit with page: " + page + " and size: " + size);
        try {
            Page<GetAllUserDetailsDto> userDetailsPage = userService.getUserList(page, size);

            if (!userDetailsPage.isEmpty()) {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("status", "success");
                responseMap.put("users", userDetailsPage.getContent());
                responseMap.put("currentPage", userDetailsPage.getNumber() + 1);
                responseMap.put("totalUsers", userDetailsPage.getTotalElements());
                responseMap.put("totalPages", userDetailsPage.getTotalPages());

                if (userDetailsPage.hasPrevious()) {
                    responseMap.put("previousPage", userService.buildUserPageURL(page - 1, size));
                }

                responseMap.put("firstPage", userService.buildUserPageURL(1, size));

                if (userDetailsPage.hasNext()) {
                    responseMap.put("nextPage", userService.buildUserPageURL(page + 1, size));
                }

                responseMap.put("lastPage", userService.buildUserPageURL(userDetailsPage.getTotalPages(), size));

                logInfo.info("Users Retrieved");
                return ResponseEntity.ok().body(responseMap);
            } else {
                logInfo.info("No users found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("status", "error", "message", "No users found"));
            }
        } catch (Exception e) {
            logInfo.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve users", e);
        }
    }


    //getUser Details with the help of token
    @GetMapping("/getUserDetail")
    //authorization ma vako adminko token lena help garxa
    public ResponseEntity<?> getAdminDetails(@RequestHeader(value = "Authorization") String userToken)
    {
        GetAllUserDetailsDto getAllUserDetailsDto=userService.getUserDetailByToken(userToken);
        return ResponseEntity.ok(getAllUserDetailsDto);

    }
}
