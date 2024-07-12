package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.*;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.AppUserDto;
import com.travelbnbtest.travelbnbtest.payload.JWTTokenDTo;
import com.travelbnbtest.travelbnbtest.payload.LoginDto;
import com.travelbnbtest.travelbnbtest.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public UserServiceImpl(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }


    @Override
    public AppUserDto createUser(AppUserDto dto) {
        AppUser appUser=dtoToEntiy(dto);
        AppUser savedUser = appUserRepository.save(appUser);
        AppUserDto appUserDto = entityToDto(savedUser);
        return appUserDto;
    }
    //converting dto to entity
    AppUser dtoToEntiy(AppUserDto dto){
        AppUser appUser=new AppUser();
        appUser.setName(dto.getName());
        appUser.setEmail(dto.getEmail());
        appUser.setUsername(dto.getUsername());
        appUser.setRole(dto.getRole());
        appUser.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        return appUser;
    }
    //converting entity to dto
    AppUserDto entityToDto(AppUser en){
        AppUserDto dto1=new AppUserDto();
        dto1.setId(en.getId());
        dto1.setName(en.getName());
        dto1.setEmail(en.getEmail());
        dto1.setUsername(en.getUsername());
        dto1.setRole(en.getRole());
        return dto1;
    }

    @Override
    public JWTTokenDTo verifyLogin(LoginDto loginDto) {
        Optional<AppUser> byUsername = appUserRepository.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            AppUser appUser = byUsername.get();
        if (  BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())){
            String token = jwtService.generateToken(appUser);
           if(token!=null){
               JWTTokenDTo jwtTokenDTo=new JWTTokenDTo();
               jwtTokenDTo.setType("JWT Token");
               jwtTokenDTo.setToken(token);
               return jwtTokenDTo;
           }else {
               throw new ResourceNotFoundException("token is null: "+token);
           }
        }else {
            throw new ResourceNotFoundException("Password is incorrect :" +loginDto.getPassword());
        }
        }else {
            throw new ResourceNotFoundException("username not exist :" +loginDto.getUsername());
        }

    }

}
