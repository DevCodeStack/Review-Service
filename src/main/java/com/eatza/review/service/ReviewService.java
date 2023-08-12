package com.eatza.review.service;

import com.eatza.review.dto.RatingUpdateDto;
import com.eatza.review.dto.RestaurantRatingDto;
import com.eatza.review.model.RestaurantReview;

public interface ReviewService {

	RestaurantRatingDto getRestaurantRating(Long restaurantId);

	RestaurantReview addRestaurantRating(Long restaurantId, Double rating, Long customerId);

	RatingUpdateDto updateRestaurantRating(Long id, Long restaurantId, Double rating, Long customerId);
	
}
