package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}