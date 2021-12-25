package com.ww.pcms.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.ww.pcms.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .build().apiInfo(carApiInfo());
	}
	
	 private ApiInfo carApiInfo() {
	        return new ApiInfoBuilder().title("WW Catalog service REST API")
	            .description("This API will provide the CRUD operations on WW Catelog resource for internal users.")
	            .contact(new Contact("Vasu", "https://vasu.com", "VasuEmailBox4@gmail.com"))
	            .version("0.0.1")
	            .build();
	    }
}
