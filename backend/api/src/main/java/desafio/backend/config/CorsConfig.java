package desafio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todas as rotas
                .allowedOrigins("http://localhost:3000") // Permitir requisições do frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permitir métodos necessários
                .allowedHeaders("*") // Permitir todos os cabeçalhos
                .exposedHeaders("Authorization") // Expor o cabeçalho de Authorization
                .allowCredentials(true); // Permitir credenciais, como cookies
    }
}
