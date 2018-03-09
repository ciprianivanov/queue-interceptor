package com.queue.consumer.listener;

import com.queue.consumer.ConsumerApplication;
import com.queue.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("consumer")
public class QueueListener {

    private static final Logger log = LoggerFactory.getLogger(QueueListener.class);

    @RabbitListener(queues = ConsumerApplication.REAL_QUEUE_NAME)
    public void receiveMessage(final Message message) {
        log.info("CONSUMER received message: {}", message.toString());
    }
}