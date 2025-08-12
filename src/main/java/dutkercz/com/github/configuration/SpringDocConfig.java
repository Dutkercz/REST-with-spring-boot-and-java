package dutkercz.com.github.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    /// Link onde podemos encontrar a espeficicação completa de uso do SpringDoc
    // https://lankydan.dev/documenting-a-spring-rest-api-following-the-openapi-specification

    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("REST API's from 0 with Java!")
                        .version("v1")
                        .description("Desenvolvimento de REST API com Java, Spring, Kubernetes e Docker")
                        .contact(new Contact()
                                .name("Cristian")
                                .email("dutkercz@gmail.com")
                                .url("https://github.com/Dutkercz")));
    }
}
