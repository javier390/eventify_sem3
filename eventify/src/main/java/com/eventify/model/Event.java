package com.eventify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    @NotNull(message = "Debe seleccionar un lugar")
    private Venue venue;
}
