package com.eatza.review.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eatza.review.dto.RatingUpdateDto;
import com.eatza.review.dto.RestaurantFetchDto;
import com.eatza.review.dto.RestaurantRatingDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.RestaurantReview;
import com.eatza.review.repository.RestaurantReviewRepository;
import com.eatza.review.util.JwtTokenUtil;
import com.eatza.review.util.ModelToDtoParser;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private RestaurantReviewRepository restaurantReviewRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${restaurant.service.search-url.restaurant}")
	private String restaurantServiceSearchUrl;
	
	@Value("${restaurant.service.review-url.restaurant}")
	private String restaurantServiceReviewUrl;
	
	@Override
	public RestaurantRatingDto getRestaurantRating(Long id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtTokenUtil.globalScopeToken);
			
			Map<String, Long> uriVariables = new HashMap<>();
			uriVariables.put("restaurantId", id);
			
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<RestaurantFetchDto> restaurant = restTemplate.exchange(restaurantServiceSearchUrl, HttpMethod.GET, entity, RestaurantFetchDto.class, uriVariables);
			
			return restaurant.hasBody()?ModelToDtoParser.parseItemRequest(restaurant.getBody()):null;
			
		} catch(Exception ex) {
			throw new ReviewException(ex.getMessage());
		}
	}

	@Override
	public RestaurantReview addRestaurantRating(Long restaurantId, Double rating, Long customerId) {
		try {
			RestaurantReview restaurantReview = RestaurantReview.builder()
													.rating(rating)
													.customerId(customerId)
													.restaurantId(restaurantId)
													.build();
			//Saving customer review
			RestaurantReview savedReview = restaurantReviewRepository.save(restaurantReview);
			//Retrieving average review for restaurant
			Double avgRating = restaurantReviewRepository.getAverageRating(restaurantId);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtTokenUtil.globalScopeToken);
			
			Map<String, Object> uriVariables = new HashMap<>();
			uriVariables.put("restaurantId", restaurantId);
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restaurantServiceReviewUrl)
					.queryParam("rating", avgRating)
					.uriVariables(uriVariables);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			//Updating rating for restaurant
			ResponseEntity<Integer> updateCount = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Integer.class);
			if(updateCount.hasBody() && updateCount.getBody() == 1)
				return savedReview;
			restaurantReviewRepository.deleteById(savedReview.getId());
			throw new ReviewException("Restaurant not valid");
			
		} catch(Exception ex) {
			if (ex instanceof ReviewException)
				throw (ReviewException) ex;
			throw new ReviewException(ex.getMessage());
		}
	}

	@Override
	public RatingUpdateDto updateRestaurantRating(Long id, Long restaurantId, Double rating, Long customerId) {
		try {
			Optional<RestaurantReview> restaurantReview = restaurantReviewRepository.findById(id);
			if(!restaurantReview.isPresent())
				throw new ReviewException("Invalid review id");
			
			if((restaurantReview.get().getCustomerId() != customerId) ||
					(restaurantReview.get().getRestaurantId() != restaurantId))
				throw new ReviewException("Invalid customer or restaurant id");
			
			if(restaurantReviewRepository.updateRating(id, rating) == 1) {
				//Retrieving average review for restaurant
				Double avgRating = restaurantReviewRepository.getAverageRating(restaurantId);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBearerAuth(JwtTokenUtil.globalScopeToken);
				
				Map<String, Object> uriVariables = new HashMap<>();
				uriVariables.put("restaurantId", restaurantId);
				
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restaurantServiceReviewUrl)
						.queryParam("rating", avgRating)
						.uriVariables(uriVariables);
				HttpEntity<String> entity = new HttpEntity<>(headers);
				
				//Updating rating for restaurant
				ResponseEntity<Integer> updateCount = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Integer.class);
				if(updateCount.hasBody() && updateCount.getBody() == 1)
					return RatingUpdateDto.builder().field("Rating").status("Field rating updated successfully").build();
				restaurantReviewRepository.deleteById(id);
				throw new ReviewException("Restaurant not valid");
			}
			throw new ReviewException("Failed to update review");
			
		} catch(Exception ex) {
			if (ex instanceof ReviewException)
				throw (ReviewException) ex;
			throw new ReviewException(ex.getMessage());
		}
	}

}
