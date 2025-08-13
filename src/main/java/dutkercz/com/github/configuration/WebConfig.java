package dutkercz.com.github.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /*
        Via EXTENSION _.xml OR _.json deprecated on spring boot 2.6
         ex: http://localhost:8080/api/person/v1/2.{xml} ou .{json}

        VIA QUERY PARAM, passamos da seguinte forma: http://localhost:8080/api/person/v1/2?mediaType=xml ou =json

        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true) //ignora o padrão que vier no HEADER da request
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON) //seta o default da request
                .mediaType("json", MediaType.APPLICATION_JSON) //configuramos os formatos disponiveis
                .mediaType("xml", MediaType.APPLICATION_XML);

        */

        // VIA HEADER PARAM, passamos o formato desejado atraves do HEADER da request

        configurer.favorParameter(false) //agora é FALSE
                .ignoreAcceptHeader(false) //ignorar o padrão que vier no HEADER da request dessa vez é FALSE
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON) //seta o default da request
                .mediaType("json", MediaType.APPLICATION_JSON) //configuramos os formatos disponiveis
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);

    }
}
