package com.backend.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida do RabbitMQ: " + message);
        // Aqui você pode implementar lógica para processar a mensagem recebida
    }
}
