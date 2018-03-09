package com.queue.interceptor.sender;

import com.queue.interceptor.InterceptorApplication;
import com.queue.model.Message;
import com.queue.producer.ProducerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("interceptor")
public class MessageSender {

    private static final Logger log = LoggerFactory.getLogger(com.queue.producer.sender.MessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(final Message message) {
        log.info("INTERCEPTOR sending message to real queue ...");
        rabbitTemplate.convertAndSend(ProducerApplication.EXCHANGE_NAME, InterceptorApplication.REAL_ROUTING_KEY, message);
    }
}