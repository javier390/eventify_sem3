package com.eventify.controller.web;

import com.eventify.model.Event;
import com.eventify.service.EventService;
import com.eventify.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/events")
public class EventViewController {

    private final EventService eventService;
    private final VenueService venueService;

    public EventViewController(EventService eventService, VenueService venueService) {
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "events/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("venues", venueService.findAll());
        return "events/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("event") Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            return "events/form";
        }
        eventService.save(event);
        // Post-Redirect-Get: procesa, guarda en BD y redirige al listado
        return "redirect:/admin/events";
    }
}
