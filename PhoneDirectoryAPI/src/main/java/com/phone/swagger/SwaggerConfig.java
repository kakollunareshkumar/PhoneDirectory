package com.phone.swagger;



import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket atividadeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.phone"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Phone Contacts",
                "Rest API Management for Phone Contacts",
                "1.0",
                "Terms of Service",
                new Contact("K.Naresh Kumar", "https://github.com/kakollunareshkumar",
                        "nareshkumarkakollu3456@gmail.com"),
                "Apache License Version 2.0",
                "https://github.com/kakollunareshkumar", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
}