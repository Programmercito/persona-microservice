package com.devsu.finapp.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "persona")
@Data
public class Personas { // Por convención, se suele nombrar la clase en singular: Persona

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(length = 100)
    private String telefono;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = false)
    private boolean estado = true;

}
