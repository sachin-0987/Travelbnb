package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.payload.AppUserDto;
import com.travelbnbtest.travelbnbtest.payload.JWTTokenDTo;
import com.travelbnbtest.travelbnbtest.payload.LoginDto;


import java.util.List;
public interface UserService {
    AppUserDto createUser(AppUserDto dto);

    JWTTokenDTo verifyLogin(LoginDto loginDto);

    void deleteUser(long userId);

    List<AppUserDto> getAllUsers(int pageSize, int pageNo, String sortBy, String sortDir);

    AppUserDto updateUser(long userId, AppUserDto dto);

    AppUserDto getUserById(long userId);
}
