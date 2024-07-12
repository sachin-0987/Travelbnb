package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}