package com.eatza.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantFetchDto {

	private Long id;
	private String name;
	private String location;
	private String cuisine;
	private int budget;
	private double rating;

}
