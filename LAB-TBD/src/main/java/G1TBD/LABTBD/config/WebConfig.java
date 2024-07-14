package G1TBD.LABTBD.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Allowing all origins, methods, and headers for CORS
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8090", "http://localhost:8081", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true); // Habilita el uso de credenciales (por ejemplo, cookies)
    }
}