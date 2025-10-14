package com.devsu.finapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.finapp.common.jms.MessageProducer;

@RestController
public class Test {
    MessageProducer messageProducer;

    Test(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/test")
    public String test() {
        messageProducer.send("test-queue", "Hello, World!");
        return "Message sent to the queue!";
    }

}
