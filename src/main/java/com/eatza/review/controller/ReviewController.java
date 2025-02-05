package com.eatza.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.review.dto.RatingUpdateDto;
import com.eatza.review.dto.RestaurantRatingDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.RestaurantReview;
import com.eatza.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ReviewController {
	
	@Autowired
	ReviewService ratingService;
	
	@GetMapping("/review/restaurant/{restaurantId}")
	@SecurityRequirement(name = "BearerAuth")
	@Operation(tags = "ReviewController", description = "Get restaurant rating by restaurant id")
	public ResponseEntity<RestaurantRatingDto> viewRestaurantRating(@PathVariable Long restaurantId) throws ReviewException {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ratingService.getRestaurantRating(restaurantId));
	}
	
	@PostMapping("/review/restaurant/{restaurantId}")
	@SecurityRequirement(name = "BearerAuth")
	@Operation(tags = "ReviewController", description = "Provide rating to restaurant ")
	public ResponseEntity<RestaurantReview> provideRestaurantRating(@PathVariable Long restaurantId, 
			@RequestParam Double rating, @RequestParam Long customerId) throws ReviewException {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ratingService.addRestaurantRating(restaurantId, rating, customerId));
	}
	
	@PutMapping("/review/restaurant/{restaurantId}/rating/{id}")
	@SecurityRequirement(name = "BearerAuth")
	@Operation(tags = "ReviewController", description = "Update rating of restaourant")
	public ResponseEntity<RatingUpdateDto> updateRestaurantRating(@PathVariable Long restaurantId, 
			@PathVariable Long id, @RequestParam Double rating, @RequestParam Long customerId) throws ReviewException {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ratingService.updateRestaurantRating(id, restaurantId, rating, customerId));
	}
	
}
