package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.Country;
import com.travelbnbtest.travelbnbtest.entity.Location;
import com.travelbnbtest.travelbnbtest.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("Select p from Property p JOIN Location l on p.location=l.id JOIN Country c on p.country=c.id where (l.name=:locationName or c.name=:locationName)AND p.dateAdded>:recentDate")
    List<Property> searchRecentProperty(@Param("locationName") String locationName, @Param("recentDate")LocalDateTime recentDate);

    @Query("Select p from Property p where p.name=:name and p.location=:location and p.country=:country")
    Optional<Property> findByNameAndLocationAndCountry(String name,Location location,Country country);
}