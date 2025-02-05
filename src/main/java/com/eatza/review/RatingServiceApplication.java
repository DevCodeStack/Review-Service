package com.eatza.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.eatza.review.config.JwtFilter;
import com.eatza.review.util.JwtTokenUtil;

@SpringBootApplication
@EnableWebMvc
public class RatingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<JwtFilter> filterRegistration(JwtTokenUtil tokenUtil){
		
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtFilter(tokenUtil));
		registrationBean.addUrlPatterns("/review/*");
		return registrationBean;
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
