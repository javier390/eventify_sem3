package com.eventify.controller.api;

import com.eventify.model.Event;
import com.eventify.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventApiController {

    private final EventService eventService;

    public EventApiController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> listar() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> obtener(@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> registrar(@Valid @RequestBody Event event) {
        Event guardado = eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
