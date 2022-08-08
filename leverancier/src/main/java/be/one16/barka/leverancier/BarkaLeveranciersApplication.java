package be.one16.barka.leverancier;

import be.one16.barka.domain.common.RestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BarkaLeveranciersApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarkaLeveranciersApplication.class, args);
    }

    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }

}
