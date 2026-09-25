package com.eventify.controller.web;

import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import com.eventify.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VenueViewController.class)
class VenueViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VenueService venueService;

    @MockBean
    private VenueRepository venueRepository;

    @Test
    void listarLugares_debeRetornar200YVistaCorrecta() throws Exception {
        Venue venue = Venue.builder().id(1L).nombre("Auditorio Central").direccion("Calle 10").capacidad(100).build();
        when(venueService.findAll()).thenReturn(List.of(venue));

        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(view().name("venues/list"))
                .andExpect(model().attributeExists("venues"));
    }

    @Test
    void listarLugaresSinRegistros_debeMostrarModeloVacio() throws Exception {
        when(venueService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("venues", Collections.emptyList()));
    }

    @Test
    void guardarLugarValido_debeRedirigirAlListado() throws Exception {
        when(venueService.save(any(Venue.class))).thenReturn(
                Venue.builder().id(1L).nombre("Auditorio Central").direccion("Calle 10").capacidad(100).build());

        mockMvc.perform(post("/admin/venues")
                        .param("nombre", "Auditorio Central")
                        .param("direccion", "Calle 10")
                        .param("capacidad", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"));
    }
}
