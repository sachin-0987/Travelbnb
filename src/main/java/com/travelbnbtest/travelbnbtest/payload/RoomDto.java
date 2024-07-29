package com.travelbnbtest.travelbnbtest.payload;

import com.travelbnbtest.travelbnbtest.entity.Property;
import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private Integer roomNumber;
    private boolean status; // false means available, true means booked
    private Property property;
}
