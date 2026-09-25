package com.eventify.config;

import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convierte el valor (id) enviado por el <select th:field="*{venue}"> del formulario
 * en la entidad Venue correspondiente, para que Spring pueda enlazarlo al objeto Event.
 */
@Component
public class VenueIdToVenueConverter implements Converter<String, Venue> {

    private final VenueRepository venueRepository;

    public VenueIdToVenueConverter(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return venueRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
