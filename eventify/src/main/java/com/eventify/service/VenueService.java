package com.eventify.service;

import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id);
    }

    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }
}
