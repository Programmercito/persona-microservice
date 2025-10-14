package com.devsu.finapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.finapp.model.entities.Cliente;
import com.devsu.finapp.model.entities.Persona;
import com.devsu.finapp.services.PersonasService;

@RestController
public class ClienteController {
    private PersonasService personasService;

    public ClienteController(PersonasService personasService) {
        this.personasService = personasService;
    }

    @PostMapping("/clientes")
    public Cliente create(@RequestBody Cliente cliente) {
        return (Cliente) personasService.save(cliente);
    }
}
