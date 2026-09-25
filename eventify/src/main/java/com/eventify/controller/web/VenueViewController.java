package com.eventify.controller.web;

import com.eventify.model.Venue;
import com.eventify.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/venues")
public class VenueViewController {

    private final VenueService venueService;

    public VenueViewController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("venues", venueService.findAll());
        return "venues/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("venue", new Venue());
        return "venues/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("venue") Venue venue, BindingResult result) {
        if (result.hasErrors()) {
            return "venues/form";
        }
        venueService.save(venue);
        // Post-Redirect-Get: evita el reenvío del formulario al refrescar
        return "redirect:/admin/venues";
    }
}
