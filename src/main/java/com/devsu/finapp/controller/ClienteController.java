package com.devsu.finapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.finapp.model.entities.Cliente;

import com.devsu.finapp.services.ClienteService;;

@RestController
public class ClienteController {
    private ClienteService clientesService;

    public ClienteController(ClienteService clientesService) {
        this.clientesService = clientesService;
    }

    @PostMapping("/clientes")
    public Cliente create(@RequestBody Cliente cliente) {
        return clientesService.save(cliente);
    }
}
