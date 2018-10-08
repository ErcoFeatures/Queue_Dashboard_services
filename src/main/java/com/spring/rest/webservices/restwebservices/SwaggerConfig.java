package com.spring.rest.webservices.restwebservices;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


// Configuration
@Configuration
//Enable Swagger

@EnableSwagger2

public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact("Trispa", "https://github.com/Trispa", "patricediouf@hotmail.fr");
    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awsome API Documentation", "Awsome API Description", "1.0", "urn:tos",
            DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

    public static final Set<String> DEFAULT_PRODUER_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json", "application/xml"));



    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUER_AND_CONSUMES)
                .consumes(DEFAULT_PRODUER_AND_CONSUMES);
    }

}
