package rest.api.constellationsreservations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ConstellationsReservationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstellationsReservationsApplication.class, args);
    }

//    @Bean
//    CommandLineRunner migratePasswords(UserPasswordMigrationService service) {
//        return args -> service.encryptAllPlainPasswords();
//    }




}
