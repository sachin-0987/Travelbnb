package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.payload.FavouriteDto;
import com.travelbnbtest.travelbnbtest.service.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

    private FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping("/addFavourite")
    public ResponseEntity<FavouriteDto> addFavourite(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId,
            @RequestBody FavouriteDto favouriteDto
            ){
        FavouriteDto savedStatus = favouriteService.addFavourite(user, propertyId, favouriteDto);
        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    @GetMapping("/getFavouriteByUser")
    public ResponseEntity<List<FavouriteDto>> getFavouriteByUser(
            @AuthenticationPrincipal AppUser user
    ){
        List<FavouriteDto> favouriteByUser = favouriteService.getFavouriteByUser(user);
        return new ResponseEntity<>(favouriteByUser,HttpStatus.OK);
    }

    @PutMapping("/{favId}")
    public ResponseEntity<FavouriteDto> updateFavorite(
            @AuthenticationPrincipal AppUser user,
            @PathVariable long favId,
            @RequestParam long propertyId,
            @RequestBody FavouriteDto favouriteDto
    ){
        FavouriteDto dto = favouriteService.updateFavorite(user, favId, propertyId, favouriteDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}
