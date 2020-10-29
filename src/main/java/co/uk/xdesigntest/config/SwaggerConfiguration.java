package co.uk.xdesigntest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Samuel Catalano
 * @since October, 2020
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final String BASE_PACKAGE = "co.uk.xdesigntest";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.customApiInfo());
    }

    /**
     * ApiInfo custom configuration.
     * @return custom configuration
     */
    private ApiInfo customApiInfo() {
        return new ApiInfoBuilder().title("XDesign Open REST API")
                .description("xDesign - Code Challenge")
                .contact(new Contact("Samuel Catalano", "https://www.linkedin.com/in/samuelcatalano/", "samuel.catalano@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}