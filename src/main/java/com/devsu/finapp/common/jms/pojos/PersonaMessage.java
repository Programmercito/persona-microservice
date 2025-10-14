package com.devsu.finapp.common.jms.pojos;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PersonaMessage implements Serializable {
    private Long id;
    private String tipoCuenta;
}
