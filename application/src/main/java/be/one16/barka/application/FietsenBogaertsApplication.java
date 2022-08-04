package be.one16.barka.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("be.one16.barka")
@ComponentScan("be.one16.barka")
@EnableJpaRepositories("be.one16.barka")
public class FietsenBogaertsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FietsenBogaertsApplication.class, args);
    }
}
