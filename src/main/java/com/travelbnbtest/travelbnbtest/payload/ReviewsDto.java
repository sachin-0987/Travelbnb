package com.travelbnbtest.travelbnbtest.payload;


import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Property;
import lombok.Data;

@Data
public class ReviewsDto {

    private Long id;
    private Integer rating;
    private String description;
    private AppUser appUser;
    private Property property;
}
