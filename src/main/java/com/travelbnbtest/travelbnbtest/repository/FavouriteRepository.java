package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Favourite;
import com.travelbnbtest.travelbnbtest.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    @Query("Select f from Favourite f where f.appUser=:user")
    List<Favourite> findFavouriteByUser(@Param("user") AppUser user);

    @Query("Select f from Favourite f where f.appUser=:user and f.property=:property")
    Optional<Favourite> findByUserAndProperty(@Param("user") AppUser user,@Param("property") Property property);
}