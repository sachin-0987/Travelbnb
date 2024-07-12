package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.payload.FavouriteDto;

import java.util.List;

public interface FavouriteService {
    FavouriteDto addFavourite(AppUser user, long propertyId, FavouriteDto favouriteDto);

    List<FavouriteDto> getFavouriteByUser(AppUser user);

    FavouriteDto updateFavorite(AppUser user, long favId, long propertyId, FavouriteDto favouriteDto);
}
