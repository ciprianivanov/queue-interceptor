package com.queue.producer.sender;

import com.queue.model.Message;
import com.queue.producer.ProducerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Profile("producer")
public class MessageSender {

    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendMessage() {
        final Message message = new Message("Hello there!", new Random().nextInt(50), false);
        log.info("PRODUCER sending message to fake queue ...");
        rabbitTemplate.convertAndSend(ProducerApplication.EXCHANGE_NAME, ProducerApplication.ROUTING_KEY, message);
    }
}