package com.eatza.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eatza.review.model.RestaurantReview;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {
	
	
	@Query(nativeQuery = true, value = "select avg(rating) from eatza.review where restaurant_Id = ?1")
	public Double getAverageRating(Long restaurantId);
	
	@Query(nativeQuery = true, value = "update eatza.review set rating = ?2 where id = ?1")
	@Modifying
	public Integer updateRating(Long id, Double rating);
	
	@Query(nativeQuery = true, value = "select * from eatza.review where restaurant_Id = ?1 and customer_Id = ?2")
	public Optional<RestaurantReview> findByRestaurantAndCustomer(Long restaurantId, Long customerId);
	
}
