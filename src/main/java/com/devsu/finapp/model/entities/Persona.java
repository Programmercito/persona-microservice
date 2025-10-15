package com.devsu.finapp.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance; import jakarta.persistence.InheritanceType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas", uniqueConstraints = {
    @UniqueConstraint(name = "UK_PERSONA_IDENTIFICACION", columnNames = {"id_identificacion", "tipo_identificacion"})
})
public class Persona {

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

    @Column(name = "genero", length = 1, nullable = false)
    private String genero;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDateTime fechaNacimiento;

    @Column(name = "id_identificacion", nullable = false, length = 100)
    private String idIdentificacion;

    @Column(name = "tipo_identificacion", nullable = false, length = 100)
    private String tipoIdentificacion;
}
