package com.hamroDaraz.daraz.config;



import com.hamroDaraz.daraz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserUserDetail::new)
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + username);
                    return new UsernameNotFoundException("No user found");
                });

    }
}
