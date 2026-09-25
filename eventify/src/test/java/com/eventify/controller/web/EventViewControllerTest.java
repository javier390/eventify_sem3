package com.eventify.controller.web;

import com.eventify.model.Event;
import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import com.eventify.service.EventService;
import com.eventify.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventViewController.class)
class EventViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private VenueService venueService;

    @MockBean
    private VenueRepository venueRepository;

    @Test
    void listarEventos_debeRetornar200YVistaCorrectaConAtributoEnModelo() throws Exception {
        Venue venue = Venue.builder().id(1L).nombre("Auditorio Central").direccion("Calle 10").capacidad(100).build();
        Event evento = Event.builder()
                .id(1L)
                .nombre("Conferencia Java")
                .descripcion("Charla técnica")
                .fecha(LocalDate.of(2026, 10, 15))
                .venue(venue)
                .build();

        when(eventService.findAll()).thenReturn(List.of(evento));

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attribute("events", List.of(evento)));
    }

    @Test
    void listarEventosSinRegistros_debeMostrarModeloVacio() throws Exception {
        when(eventService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/list"))
                .andExpect(model().attribute("events", Collections.emptyList()));
    }

    @Test
    void mostrarFormulario_debeRetornarVistaDeFormularioConVenuesDisponibles() throws Exception {
        when(venueService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/events/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/form"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attributeExists("venues"));
    }

    @Test
    void guardarEventoValido_debeRedirigirAlListado() throws Exception {
        Venue venue = Venue.builder().id(1L).nombre("Auditorio Central").direccion("Calle 10").capacidad(100).build();
        when(venueRepository.findById(1L)).thenReturn(java.util.Optional.of(venue));
        when(eventService.save(any(Event.class))).thenReturn(
                Event.builder().id(1L).nombre("Conferencia Java").descripcion("Charla técnica")
                        .fecha(LocalDate.of(2026, 10, 15)).venue(venue).build());

        mockMvc.perform(post("/admin/events")
                        .param("nombre", "Conferencia Java")
                        .param("descripcion", "Charla técnica")
                        .param("fecha", "2026-10-15")
                        .param("venue", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));
    }
}
