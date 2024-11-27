package com.hamroDaraz.daraz.config;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
//@RequiredArgsConstructor
public class SecurityConfig extends WebMvcConfigurationSupport {
    @Autowired
    private final JwtAuthenticationFilter filter;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private CustomAdminDetailService customAdminDetailService;


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(JwtAuthenticationFilter filter){this.filter=filter;}

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }

    //User entity matra huda userAuthenticationProvider matra banaya hunxa
    @Bean
    public AuthenticationProvider userAuthenticationProvider()
    {
        var authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService); //yesle username check
        authenticationProvider.setPasswordEncoder(passwordEncoder()); //pw lai encode garer check so dao help to check username and pw
        return  authenticationProvider;
    }
    //admin huda chai adminAuthenticationProvider pani banuana like this:
    @Bean
    public AuthenticationProvider adminAuthenticationProvider()  //adminAuthentcationprovider add garna parxa i.e admin ko username ra pw thika xa xaina check garxa
    {
        var authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customAdminDetailService); //authenticationProvider vitra cutomAdminDetailService lai implement garan parana vako lew
        authenticationProvider.setPasswordEncoder(passwordEncoder()); //passwordEncoder yeha set garesi balla AdminServiceimpl ma use garna sakinxa
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager()
    {
        //UserAuthenticationProvider huda matra i.e only for User entity
//        return new ProviderManager(userAuthenticationProvider());

        //Admin ra User 2 tai huda chai :
        return new ProviderManager(Arrays.asList(userAuthenticationProvider(),adminAuthenticationProvider()));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http.csrf(csrf->csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests((auth)->auth
//
                    .requestMatchers("/api/v1/auth/**","/api/v1/user/userList").permitAll()
                            .requestMatchers("/api/v1/user/getAll").hasAuthority("Admin")
//                            .requestMatchers("/api/v1/user/getUserDetail").authenticated()//jasko vaya ni token vaya pugxa
                            .anyRequest().permitAll()
            )
            .sessionManagement(session->session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            //UserAuthenticationProvider huda matra( i.e only for User entity) addFilterBefor 1 gareko
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);//yo line le k batauxa vana usernamepassowrd verify garana vanada
        //pahila token validate garana vanera filter rakheko ho

        return http.build();

    }

}
