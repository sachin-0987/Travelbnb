package com.travelbnbtest.travelbnbtest.payload;

import com.travelbnbtest.travelbnbtest.entity.Country;
import com.travelbnbtest.travelbnbtest.entity.Location;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PropertyDto {
    private Long id;
    private String name;
    private Integer noGuests;
    private Integer noBedrooms;
    private Integer noBathrooms;
    private Integer nightlyPrice;
    private Country country;
    private Location location;
    private LocalDate checkIn;
    private LocalDate checkOut;

}
