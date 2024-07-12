package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.entity.Reviews;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.ReviewsDto;
import com.travelbnbtest.travelbnbtest.repository.AppUserRepository;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import com.travelbnbtest.travelbnbtest.repository.ReviewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewsRepository reviewsRepository;

    private PropertyRepository propertyRepository;

    public ReviewServiceImpl(ReviewsRepository reviewsRepository, PropertyRepository propertyRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
    }


    //dto to entity
    Reviews dtoToEntity(ReviewsDto reviewsDto) {
        Reviews entity = new Reviews();
        entity.setRating(reviewsDto.getRating());
        entity.setDescription(reviewsDto.getDescription());
        entity.setAppUser(reviewsDto.getAppUser());
        entity.setProperty(reviewsDto.getProperty());

        return entity;
    }
    //entity to dto

    ReviewsDto entityToDto(Reviews reviews) {
        ReviewsDto dto = new ReviewsDto();
        dto.setId(reviews.getId());
        dto.setRating(reviews.getRating());
        dto.setDescription(reviews.getDescription());
        dto.setAppUser(reviews.getAppUser());
        dto.setProperty(reviews.getProperty());
        return dto;
    }

    @Override
    public ReviewsDto addReview(AppUser user, long propertyId, ReviewsDto reviewsDto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property not found with id: " + propertyId)
        );
        if (reviewsRepository.findReviewsByUser(user, property) != null) {
            throw new ResourceNotFoundException("Already review exist");
        } else {
            reviewsDto.setProperty(property);
            reviewsDto.setAppUser(user);
            if (reviewsDto.getRating() <= 5) {
                Reviews reviews = dtoToEntity(reviewsDto);
                Reviews save = reviewsRepository.save(reviews);
                ReviewsDto dto = entityToDto(save);
                return dto;
            } else {
                throw new ResourceNotFoundException("Rating is greater than 5 ");
            }
        }

    }

    @Override
    public ReviewsDto updateReview(long reviewId, AppUser user, long propertyId, ReviewsDto reviewsDto) {
        Reviews reviews=null;
        reviews = reviewsRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review not found with id: " + reviewId)
        );
        reviewsDto.setAppUser(user);

        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property not found with id: " + propertyId)
        );
        reviewsDto.setProperty(property);

        reviews = dtoToEntity(reviewsDto);
        reviews.setId(reviewId);

        Reviews save = reviewsRepository.save(reviews);
        ReviewsDto dto = entityToDto(save);
        return dto;
    }

    @Override
    public List<ReviewsDto> getAllReview(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }else {
            pageable=PageRequest.of(pageNo,pageSize);
        }

        Page<Reviews> all = reviewsRepository.findAll(pageable);
        List<Reviews> content = all.getContent();
        List<ReviewsDto> collect = content.stream().map(e -> entityToDto(e)).collect(Collectors.toList());
        return collect;

    }

    @Override
    public List<ReviewsDto> getUserReview(AppUser user) {
        List<Reviews> byUserReviews = reviewsRepository.findByUserReviews(user);
        List<ReviewsDto> collect = byUserReviews.stream().map(e -> entityToDto(e)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteReview(AppUser user, long propertyId) {
        Property property = propertyRepository.findById(propertyId).get();

        reviewsRepository.deleteReviewsByUserAndProperty(user,property);
    }

}
