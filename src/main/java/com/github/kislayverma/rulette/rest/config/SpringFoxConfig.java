package com.github.kislayverma.rulette.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.kislayverma.rulette.rest.api"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Rulette Server API",
            "Rulette Server REST API",
            "v1",
            "",
            new Contact("Kislay Verma", "https://kislayverma.com", "kislay.nsit@gmail.com"),
            "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}
