package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Room;
import com.travelbnbtest.travelbnbtest.payload.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(long propertyId, AppUser user, BookingDto bookingDto, long roomId);

    List<Room> getAvailableRooms(long propertyId);
}
