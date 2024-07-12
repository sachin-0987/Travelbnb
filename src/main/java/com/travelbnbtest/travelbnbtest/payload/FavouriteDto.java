package com.travelbnbtest.travelbnbtest.payload;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Property;
import lombok.Data;

@Data
public class FavouriteDto {

    private Long id;
    private Boolean status;
    private Property property;
    private AppUser appUser;
}
