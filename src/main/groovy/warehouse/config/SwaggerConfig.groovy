package warehouse.config

import org.springframework.context.annotation.Bean
import springfox.documentation.spring.web.plugins.Docket

class SwaggerConfig {
    @Bean
    Docket api() {
        new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }
}
