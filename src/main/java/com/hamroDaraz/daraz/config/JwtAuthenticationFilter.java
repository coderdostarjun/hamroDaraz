package com.hamroDaraz.daraz.config;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private CustomAdminDetailService customAdminDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        String userType = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtTokenHelper.extractUsernameFromToken(token);
                userType = jwtTokenHelper.extractUserTypeFromToken(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails=null;
//                    //for user only
//                    userDetails = customUserDetailService.loadUserByUsername(email);

                    //for user and admin
                    if("Admin".equals(userType))
                    {
                        userDetails=customAdminDetailService.loadUserByUsername(email);
                    }
                    else
                    {
                        userDetails = customUserDetailService.loadUserByUsername(email);
                    }

                    if (jwtTokenHelper.validateToken(token, userDetails)) {
                        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        SecurityContextHolder.clearContext();
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Invalid token\"}");
                        return;
                    }
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Expired token\"}");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid token\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

