package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    @Query("Select r from Reviews r where r.appUser=:user and r.property=:property")
    Reviews findReviewsByUser(@Param("user") AppUser user, @Param("property") Property property);


    @Query("Select r from Reviews r where r.appUser=:user")
    List<Reviews> findByUserReviews(@Param("user") AppUser user);

    @Modifying
    @Transactional
    @Query("Delete from Reviews r where r.appUser=:user and r.property=:property")
    void deleteReviewsByUserAndProperty(@Param("user") AppUser user, @Param("property") Property property);

}