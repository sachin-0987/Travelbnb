package com.travelbnbtest.travelbnbtest.config;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.repository.AppUserRepository;
import com.travelbnbtest.travelbnbtest.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public JWTRequestFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //for extracting token from Authorization header
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader !=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            String username = jwtService.getUsername(token);
          //   System.out.println(username);
            Optional<AppUser> byUsername = appUserRepository.findByUsername(username);
            if (byUsername.isPresent()){
                AppUser appUser = byUsername.get();
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(appUser,null, Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                throw new ResourceNotFoundException("User not present with username: "+username);
            }

        }
        filterChain.doFilter(request,response);
    }
}
