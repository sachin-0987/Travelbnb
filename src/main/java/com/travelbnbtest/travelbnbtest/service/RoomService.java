package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.payload.RoomDto;

public interface RoomService {
    RoomDto addRooms(long propertyId, RoomDto roomDto);


    RoomDto updateRooms(long roomId, long propertyId, RoomDto dto);
}
