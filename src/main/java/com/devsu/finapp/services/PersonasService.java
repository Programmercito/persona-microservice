package com.devsu.finapp.services;

import org.springframework.stereotype.Service;

import com.devsu.finapp.common.jms.MessageProducer;
import com.devsu.finapp.common.jms.pojos.PersonaMessage;
import com.devsu.finapp.model.entities.Cliente;
import com.devsu.finapp.model.entities.Persona;
import com.devsu.finapp.model.repositories.ClienteRespository;

@Service
public class PersonasService {
    ClienteRespository clienteRespository;
    MessageProducer messageProducer;

    public PersonasService(ClienteRespository clienteRespository,
            MessageProducer messageProducer) {
        this.clienteRespository = clienteRespository;
        this.messageProducer = messageProducer;
    }

    public Cliente save(Cliente persona) {
        Cliente resul = clienteRespository.save(persona);
        PersonaMessage message = new PersonaMessage();
        message.setId(resul.getId());
        messageProducer.send("create-first-account-queue", message);
        return resul;
    }
}
