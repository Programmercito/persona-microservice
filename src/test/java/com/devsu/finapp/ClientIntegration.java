package com.devsu.finapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.finapp.model.entities.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Asegura que cada test se ejecute en una transacción que se revierte al final
public class ClientIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenPostCliente_thenClienteIsCreated() throws Exception {
        // 1. Preparación (Arrange)
        Cliente newCliente = new Cliente();
        newCliente.setNombres("Marianela");
        newCliente.setApellidos("Montalvo");
        newCliente.setGenero("F");
        newCliente.setFechaNacimiento(LocalDateTime.of(1992, 5, 21, 0, 0));
        newCliente.setIdIdentificacion("0987654321");
        newCliente.setTipoIdentificacion("CC");
        newCliente.setDireccion("Amazonas y NNUU");
        newCliente.setTelefono("0987654321");
        newCliente.setPassword("supersecret");

        // 2. Acción (Act) y 3. Verificación (Assert)
        mockMvc.perform(post("/api/identidad/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCliente)))
                .andExpect(status().isOk()) // O isCreated() dependiendo de tu controller
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombres").value("Marianela"))
                .andExpect(jsonPath("$.apellidos").value("Montalvo"))
                .andExpect(jsonPath("$.password").doesNotExist()); // Verifica que el password no se devuelva en la respuesta
    }
}
