package de.woody64k.services.person.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Konfiguriert Swagger.
 * 
 * @author snsauerb
 */
@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    /**
     * Schaltet die Swagger-UI ein.
     * 
     * @return
     */
    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.basePackage("de.woody64k.services.person")).paths(PathSelectors.any())
		.build().apiInfo(metadata());
    }

    private ApiInfo metadata() {
	return new ApiInfoBuilder().title("Person-Service")
		.description("Proviedes an Crud interface for Personal-Data.").build();
    }
}
