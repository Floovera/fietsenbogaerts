package be.one16.barka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern="be.one16.barka.*.*Application"))
public class FietsenBogaertsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FietsenBogaertsApplication.class, args);
    }
}
