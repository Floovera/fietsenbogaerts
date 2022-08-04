package be.one16.barka;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@EntityScan("be.one16.barka")
@ComponentScan("be.one16.barka")
@EnableJpaRepositories("be.one16.barka")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = BaseIntegrationTesting.DataSourceInitializer.class)
public class BaseIntegrationTesting {

    @Container
    private static final MySQLContainer<?> database = new MySQLContainer<>("mysql:8.0.30-debian")
            .withDatabaseName("barka_db")
            .withUsername("user")
            .withPassword("password");

    @TestConfiguration
    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword(),
                    "spring.jpa.generate-ddl=true",
                    "spring.jpa.hibernate.ddl-auto=create-drop");
        }
    }
}
