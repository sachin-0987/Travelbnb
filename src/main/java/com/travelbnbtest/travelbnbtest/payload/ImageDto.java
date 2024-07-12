package com.travelbnbtest.travelbnbtest.payload;

import com.travelbnbtest.travelbnbtest.entity.Property;
import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private String imageUrl;
    private Property property;
}
