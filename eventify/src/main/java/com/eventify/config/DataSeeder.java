package com.eventify.config;

import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner sembrarDatos(VenueRepository venueRepository) {
        return args -> {
            if (venueRepository.count() == 0) {
                venueRepository.save(Venue.builder()
                        .nombre("Auditorio Central")
                        .direccion("Calle 10 # 20-30")
                        .capacidad(200)
                        .build());
                venueRepository.save(Venue.builder()
                        .nombre("Salón Norte")
                        .direccion("Carrera 45 # 12-08")
                        .capacidad(80)
                        .build());
            }
        };
    }
}
