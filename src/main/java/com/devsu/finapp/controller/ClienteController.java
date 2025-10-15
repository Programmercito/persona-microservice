package com.devsu.finapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.finapp.model.entities.Cliente;

import com.devsu.finapp.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private ClienteService clientesService;

    public ClienteController(ClienteService clientesService) {
        this.clientesService = clientesService;
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Long id) {
        return clientesService.findById(id);
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) {
        return clientesService.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updatedCliente = clientesService.update(id, clienteDetails);
        return updatedCliente;
    }

    @PatchMapping("/{id}")
    public Cliente partialUpdate(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updatedCliente = clientesService.update(id, clienteDetails);
        return updatedCliente;
    }

    @DeleteMapping("/{id}")
    public Cliente delete(@PathVariable Long id) {
        Cliente cliente = clientesService.findById(id);
        cliente.setEstado(false);
        clientesService.update(id, cliente);
        return cliente;
    }
}
