package com.travelbnbtest.travelbnbtest.payload;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Property;
import lombok.Data;

@Data
public class BookingDto {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Integer price;
    private Integer totalNights;
    private AppUser appUser;
    private Property property;
}
