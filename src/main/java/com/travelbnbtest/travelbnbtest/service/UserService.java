package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.payload.AppUserDto;
import com.travelbnbtest.travelbnbtest.payload.JWTTokenDTo;
import com.travelbnbtest.travelbnbtest.payload.LoginDto;


import java.util.List;
public interface UserService {
    AppUserDto createUser(AppUserDto dto);

    JWTTokenDTo verifyLogin(LoginDto loginDto);
}
