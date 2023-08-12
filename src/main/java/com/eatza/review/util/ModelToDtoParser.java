package com.eatza.review.util;

import com.eatza.review.dto.RestaurantFetchDto;
import com.eatza.review.dto.RestaurantRatingDto;

public class ModelToDtoParser {
	
	public static RestaurantRatingDto parseItemRequest(RestaurantFetchDto fetchDto) {
		return RestaurantRatingDto.builder()
			.restaurantId(fetchDto.getId())
			.rating(fetchDto.getRating())
			.build();
	}

}
