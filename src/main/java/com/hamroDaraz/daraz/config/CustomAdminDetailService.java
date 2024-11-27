package com.hamroDaraz.daraz.config;



import com.hamroDaraz.daraz.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAdminDetailService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByEmail(username)
                //adminuserdetail ma rakhera tesko constructor ma admin passs garna
                // Step 4: If an Admin is found, convert it to AdminUserDetail
                .map(AdminUserDetail::new) //Mapping Admin to AdminUserDetail//AdminUserDetailMethod sanga map gardekoxa
                // Step 5: If no Admin is found, throw a UsernameNotFoundException
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + username);
                    return new UsernameNotFoundException("No user found");
                });
    }
}
