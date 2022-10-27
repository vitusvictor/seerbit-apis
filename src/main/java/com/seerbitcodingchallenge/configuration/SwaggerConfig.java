package com.seerbitcodingchallenge.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfo("SEERBIT CODING INTERVIEW CHALLENGE",
                "With three endpoints for get, delete and post",
                "1.0",
                "this version is free to all",
                new springfox.documentation.service.Contact("SEERBIT CODING INTERVIEW CHALLENGE", "", ""),
                "Api License",
                "",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
