package com.eventify.controller.api;

import com.eventify.model.Venue;
import com.eventify.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueApiController {

    private final VenueService venueService;

    public VenueApiController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<List<Venue>> listar() {
        return ResponseEntity.ok(venueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> obtener(@PathVariable Long id) {
        return venueService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venue> registrar(@Valid @RequestBody Venue venue) {
        Venue guardado = venueService.save(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
