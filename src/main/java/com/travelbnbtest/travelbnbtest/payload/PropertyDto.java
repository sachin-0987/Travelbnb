package com.travelbnbtest.travelbnbtest.payload;

import com.travelbnbtest.travelbnbtest.entity.Country;
import com.travelbnbtest.travelbnbtest.entity.Location;
import lombok.Data;

@Data
public class PropertyDto {
    private Long id;
    private String name;
    private Integer noGuests;
    private Integer noBedrooms;
    private Integer noBathrooms;
    private Integer price;
    private Country country;
    private Location location;
}
