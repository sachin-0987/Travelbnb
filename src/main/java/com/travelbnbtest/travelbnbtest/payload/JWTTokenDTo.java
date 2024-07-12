package com.travelbnbtest.travelbnbtest.payload;

import lombok.Data;

@Data
public class JWTTokenDTo {
    private String type;
    private String token;
}
