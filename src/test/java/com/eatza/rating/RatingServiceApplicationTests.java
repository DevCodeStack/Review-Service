package com.eatza.rating;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.eatza.review.RatingServiceApplication;
import com.eatza.review.config.JwtFilter;

@ExtendWith(MockitoExtension.class)
class RatingServiceApplicationTests {

	@Mock
	RatingServiceApplication ratingServiceApplication;
	
	com.eatza.review.util.JwtTokenUtil tokenUtil;
	
	@Test
	void contextLoads() {
		
		ratingServiceApplication = new RatingServiceApplication();
		FilterRegistrationBean<JwtFilter> registrationBean = 
				ratingServiceApplication.filterRegistration(tokenUtil);
		assertThat(registrationBean.getUrlPatterns().equals(Arrays.array("/review/*")));
		
	}

}
