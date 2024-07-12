package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.payload.AppUserDto;
import com.travelbnbtest.travelbnbtest.payload.JWTTokenDTo;
import com.travelbnbtest.travelbnbtest.payload.LoginDto;
import com.travelbnbtest.travelbnbtest.repository.AppUserRepository;
import com.travelbnbtest.travelbnbtest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService appUserService;
    private AppUserRepository appUserRepository;

    public UserController(UserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody AppUserDto dto,
            BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        if (appUserRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>("Email Exists", HttpStatus.BAD_REQUEST);
        }
        if (appUserRepository.existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>("Username Exists", HttpStatus.BAD_REQUEST);
        }
        AppUserDto createdUser = appUserService.createUser(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto) {
        JWTTokenDTo token = appUserService.verifyLogin(loginDto);
        if (token!=null){
            return new ResponseEntity<>(token,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid token",HttpStatus.OK);
        }

    }
}
