package com.backend.controllers;



import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate template;

    public void send(String message) {
        rabbitTemplate.convertAndSend("myQueue", message);
        template.convertAndSend("/topic/messages", message);
        System.out.println("Mensagem enviada para RabbitMQ e WebSocket: " + message);
    }
}
