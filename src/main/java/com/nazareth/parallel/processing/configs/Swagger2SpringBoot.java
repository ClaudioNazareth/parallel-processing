package com.nazareth.parallel.processing.configs;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class Responsible for setting up Swagger2 and automatically mapping the rest APIs
 *
 * @author Claudio Nazareth
 */
@Configuration
@EnableSwagger2
public class Swagger2SpringBoot {

  @Bean
  public Docket documentation() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(regex("/api/v1/.*"))
        .build()
        .pathMapping("/")
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo());
  }

  @Bean
  UiConfiguration uiConfig() {
    return new UiConfiguration("validatorUrl", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Registration Service").version("1.0").build();
  }
}
