package com.devsu.finapp.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Movimiento {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal movimiento;
    private BigDecimal saldo;
    private Cuenta cuenta;
    private boolean estado;

}
