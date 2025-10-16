package com.devsu.finapp.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.devsu.finapp.common.exceptions.ResourceNotFoundException;
import com.devsu.finapp.common.jms.MessageProducer;
import com.devsu.finapp.common.jms.pojos.PersonaMessage;
import com.devsu.finapp.model.entities.Cliente;
import com.devsu.finapp.model.repositories.ClienteRespository;

@Service
public class ClienteService {
    ClienteRespository clienteRespository;
    MessageProducer messageProducer;
    PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRespository clienteRespository,
            MessageProducer messageProducer, PasswordEncoder passwordEncoder) {
        this.clienteRespository = clienteRespository;
        this.messageProducer = messageProducer;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente findById(Long id) {
        return clienteRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    public Cliente save(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        Cliente resul = clienteRespository.save(cliente);
        // Enviar mensaje de forma asíncrona
        sendCreationMessage(resul);
        return resul;
    }

    public Cliente update(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        // Actualizar datos de Persona
        if (clienteDetails.getNombres() != null)
            cliente.setNombres(clienteDetails.getNombres());
        if (clienteDetails.getApellidos() != null)
            cliente.setApellidos(clienteDetails.getApellidos());
        if (clienteDetails.getGenero() != null)
            cliente.setGenero(clienteDetails.getGenero());
        if (clienteDetails.getFechaNacimiento() != null)
            cliente.setFechaNacimiento(clienteDetails.getFechaNacimiento());
        if (clienteDetails.getIdIdentificacion() != null)
            cliente.setIdIdentificacion(clienteDetails.getIdIdentificacion());
        if (clienteDetails.getTipoIdentificacion() != null)
            cliente.setTipoIdentificacion(clienteDetails.getTipoIdentificacion());
        if (clienteDetails.getDireccion() != null)
            cliente.setDireccion(clienteDetails.getDireccion());
        if (clienteDetails.getTelefono() != null)
            cliente.setTelefono(clienteDetails.getTelefono());

        // Actualizar datos de Cliente
        if (clienteDetails.getEstado() != null)
            cliente.setEstado(clienteDetails.getEstado());

        // Manejar la contraseña de forma segura
        if (clienteDetails.getPassword() != null && !clienteDetails.getPassword().isEmpty()) {
            cliente.setPassword(passwordEncoder.encode(clienteDetails.getPassword()));
        }
        Cliente updatedCliente = clienteRespository.save(cliente);
        // Enviar mensaje de forma asíncrona
        sendUpdateMessage(updatedCliente);
        return updatedCliente;
    }

    @Async
    public void sendCreationMessage(Cliente cliente) {
        PersonaMessage message = new PersonaMessage();
        message.setId(cliente.getId());
        message.setNombres(cliente.getNombres());
        message.setApellidos(cliente.getApellidos());
        messageProducer.send("create-client", message);
    }

    @Async
    public void sendUpdateMessage(Cliente cliente) {
        PersonaMessage message = new PersonaMessage();
        message.setId(cliente.getId());
        message.setEstado(cliente.getEstado());
        messageProducer.send("update-client", message);
    }

}
