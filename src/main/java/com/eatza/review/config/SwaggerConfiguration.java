package com.eatza.review.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
    public Docket api() { 
		  return new Docket(DocumentationType.SWAGGER_2)
			        .select()
			        .apis(RequestHandlerSelectors.basePackage("com.eatza.review"))
			        .paths(PathSelectors.any())
			        .build()
			        .apiInfo(apiInfo())
			        .securitySchemes(Collections.singletonList(getApiKey()));                                       
    }

	private SecurityScheme getApiKey() {
		return new ApiKey("Bearer", "Authorization", "Header");
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder builder = new ApiInfoBuilder();
		return builder.title("Review Service APIs")
				.description("Review service for eatza restaurants")
				.version("0.0.1")
				.build();
	}
}
