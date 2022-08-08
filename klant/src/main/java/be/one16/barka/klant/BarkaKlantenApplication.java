package be.one16.barka.klant;

import be.one16.barka.domain.common.RestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BarkaKlantenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarkaKlantenApplication.class, args);
    }

    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
}
