package com.travelbnbtest.travelbnbtest.service;


import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.payload.ReviewsDto;

import java.util.List;

public interface ReviewService {

    ReviewsDto addReview(AppUser user, long propertyId, ReviewsDto reviewsDto);


    ReviewsDto updateReview(long reviewId, AppUser user, long propertyId, ReviewsDto reviewsDto);


    List<ReviewsDto> getAllReview(int pageSize, int pageNo, String sortBy, String sortDir);

    List<ReviewsDto> getUserReview(AppUser user);

    void deleteReview(AppUser user, long propertyId);
}
