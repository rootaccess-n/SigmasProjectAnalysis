package rest.api.constellationsreservations.Config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true) // ставим baseline, если история сломана
                .load();
        flyway.migrate(); // запускаем миграции сразу
        return flyway;
    }
}
