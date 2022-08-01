package be.one16.barka.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("be.one16.barka")
public class FietsenBogaertsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FietsenBogaertsApplication.class, args);
    }
}
