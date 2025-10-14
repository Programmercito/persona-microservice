package com.devsu.finapp.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devsu.finapp.common.jms.MessageProducer;
import com.devsu.finapp.common.jms.pojos.PersonaMessage;
import com.devsu.finapp.model.entities.Cliente;
import com.devsu.finapp.model.entities.Persona;
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

    public Cliente save(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        Cliente resul = clienteRespository.save(cliente);
        PersonaMessage message = new PersonaMessage();
        message.setId(resul.getId());
        messageProducer.send("create-first-account-queue", message);
        return resul;
    }
}
