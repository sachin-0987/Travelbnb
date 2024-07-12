package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Reviews;
import com.travelbnbtest.travelbnbtest.payload.ReviewsDto;
import com.travelbnbtest.travelbnbtest.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {

    private ReviewService reviewService;

    public ReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/addReview")
    public ResponseEntity<ReviewsDto> addReview(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId,
            @RequestBody ReviewsDto reviewsDto
            ){
        ReviewsDto dto = reviewService.addReview(user, propertyId, reviewsDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

//here through  @AuthenticationPrincipal userId is taking
    //or alternate ,in other ways we directly supply userId through @RequestParam
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewsDto> updateReview(
            @PathVariable long reviewId,
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId,
            @RequestBody ReviewsDto reviewsDto
    ){
        ReviewsDto dto = reviewService.updateReview(reviewId, user, propertyId, reviewsDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping("/getReviewByUser")
    public ResponseEntity<List<ReviewsDto>> getUserReview(
            @AuthenticationPrincipal AppUser user
    ){
        List<ReviewsDto> userReview = reviewService.getUserReview(user);
        return new ResponseEntity<>(userReview,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReviewsDto>> getAllReview(
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ){
        List<ReviewsDto> allReview = reviewService.getAllReview(pageSize, pageNo, sortBy, sortDir);
        return new ResponseEntity<>(allReview,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteReview(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId
    ){
    reviewService.deleteReview(user, propertyId);
        return new ResponseEntity<>("Record is deleted",HttpStatus.OK);
    }
}
