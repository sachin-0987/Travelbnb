package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.payload.AppUserDto;
import com.travelbnbtest.travelbnbtest.payload.CountryDto;
import com.travelbnbtest.travelbnbtest.payload.JWTTokenDTo;
import com.travelbnbtest.travelbnbtest.payload.LoginDto;
import com.travelbnbtest.travelbnbtest.repository.AppUserRepository;
import com.travelbnbtest.travelbnbtest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam long userId){
        appUserService.deleteUser(userId);
        return new ResponseEntity<>("User is deleted !!",HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<AppUserDto>> getAllUser(
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ) {
        List<AppUserDto> allUsers = appUserService.getAllUsers(pageSize, pageNo, sortBy, sortDir);
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AppUserDto> updateUser(
            @PathVariable long userId,
            @RequestBody AppUserDto dto
    ){
        AppUserDto appUserDto = appUserService.updateUser(userId, dto);
        return new ResponseEntity<>(appUserDto,HttpStatus.OK);
    }
    @GetMapping("/id")
    public ResponseEntity<AppUserDto> getUserById(@RequestParam long userId){
        AppUserDto userById = appUserService.getUserById(userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);
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
