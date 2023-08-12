package com.eatza.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class RestaurantRatingDto {
	
	private Long restaurantId;
	private Double rating;
	
}
