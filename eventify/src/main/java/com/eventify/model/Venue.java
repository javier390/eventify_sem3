package com.eventify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del lugar es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private int capacidad;
}
