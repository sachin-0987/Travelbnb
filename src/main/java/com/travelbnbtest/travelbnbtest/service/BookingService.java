package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.payload.BookingDto;

public interface BookingService {
    BookingDto createBooking(long propertyId, AppUser user, BookingDto bookingDto);
}
