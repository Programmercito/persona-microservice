package com.devsu.finapp.common.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.devsu.finapp.common.jms.pojos.PersonaMessage;


@Service
public class MessageProducer {
    private final JmsTemplate jmsTemplate;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String destination, String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    public void send(String destination, PersonaMessage persona) {
        jmsTemplate.convertAndSend(destination, persona);
    }
}
