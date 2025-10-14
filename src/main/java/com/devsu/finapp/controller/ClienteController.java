package com.devsu.finapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.finapp.model.entities.Cliente;

import com.devsu.finapp.services.ClienteService;

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

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updatedCliente = clientesService.update(id, clienteDetails);
        return ResponseEntity.ok(updatedCliente);
    }

    @PatchMapping("/clientes/{id}")
    public ResponseEntity<Cliente> partialUpdate(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updatedCliente = clientesService.update(id, clienteDetails);
        return ResponseEntity.ok(updatedCliente);
    }
}
