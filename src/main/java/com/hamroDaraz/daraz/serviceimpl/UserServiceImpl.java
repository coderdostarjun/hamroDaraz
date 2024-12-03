package com.hamroDaraz.daraz.serviceimpl;

import com.hamroDaraz.daraz.config.JwtTokenHelper;
import com.hamroDaraz.daraz.dto.GetAllUserDetailsDto;
import com.hamroDaraz.daraz.dto.UserRegisterDto;
import com.hamroDaraz.daraz.entity.Cart;
import com.hamroDaraz.daraz.entity.User;
import com.hamroDaraz.daraz.exception.ResourceNotFoundException;
import com.hamroDaraz.daraz.repository.CartRepository;
import com.hamroDaraz.daraz.repository.EmailService;
import com.hamroDaraz.daraz.repository.UserRepository;
import com.hamroDaraz.daraz.service.UserService;
import com.hamroDaraz.daraz.service.VerificationTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    String baseurl="http://localhost:8080/api/v1/user";
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CartRepository cartRepository;

//registerUser
    @Override
    public UserRegisterDto registerUser(UserRegisterDto userDto) {
        User user=this.modelMapper.map(userDto,User.class); //map garesi aba userRegisterDto ko object bata user object create vayo
        //pw encode garna parxa later
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setCreatedTime(LocalDateTime.now());
        user.setRole("User");
        user.setEnabled(false);
        //jaba user create hunxa taba tesko cart create hunxa
        Cart cart=new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0L);
        cart.setCreatedAt(LocalDateTime.now());

        User savedSeller=userRepository.save(user);  //user ko object create hunxa save garda teslai nai pathauna createverficationtoken ma
         cartRepository.save(cart);
        String otp = String.format("%06d", (int) (Math.random() * 1000000));//generate otp code
        verificationTokenService.createVerificationToken(savedSeller, otp);//genereate gareko otp ani tesko date,expire date ,ani kuna userko otp verificationTokenService ma rakhyo
        emailService.sendVerificationEmail(savedSeller.getEmail(), otp);//related userko mail ma token send garna ko lagi
//register garersi token generate garyo, teslai verificationtokenservice ma rkaho,ani mail ma pathayo token
// token get garera thika xa xaina check garenxa if thik vaya user.setenable true gardena ani store gareko token hatidena table bata


        UserRegisterDto response=this.modelMapper.map(user, UserRegisterDto.class);
        return  response;
    }

    //getAllUserDetails
    @Override
    public List<GetAllUserDetailsDto> getAllUserDetails() {
        List<User> userList= this.userRepository.findAll();
        return userList.stream().map(user -> {
            GetAllUserDetailsDto getAllUserDetailsDtoList= this.modelMapper.map(user, GetAllUserDetailsDto.class);
            return  getAllUserDetailsDtoList;
        }).collect(Collectors.toList());

    }

    //getUserDetailsByToken
    @Override
    public GetAllUserDetailsDto getUserDetailByToken(String userToken) {
        String token = userToken.replaceAll("Bearer", " ").trim();
        String username = jwtTokenHelper.extractUsernameFromToken(token); //token ko help le admin ko username extract gardenxa
        System.out.println(username);
        User user=userRepository.findByEmail(username)
                .orElseThrow(()-> new ResourceNotFoundException("user","email",username));
        GetAllUserDetailsDto getAllUserDetailsDto=this.modelMapper.map(user, GetAllUserDetailsDto.class);
        return  getAllUserDetailsDto;
    }

    //getAllUserDetailsByPaging
    @Override
    public Page<GetAllUserDetailsDto> getUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("createdTime")));
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(user -> {
          GetAllUserDetailsDto dto = modelMapper.map(user, GetAllUserDetailsDto.class);
            return dto;
        });

    }
    @Override
    public String buildUserPageURL(int page, int size) {
        return baseurl + "/userList?page=" + page + "&size=" + size;
    }

}
















//The term "mapping" in this context refers to the process of transferring data or values from one object (source) to another object (destination)
//that typically have similar properties.
//When you map an object, you are copying the values from fields in the source object (e.g., User)
//into fields in the destination object (e.g., UserRegisterDto).