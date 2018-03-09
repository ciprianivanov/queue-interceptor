package com.queue.interceptor.listener;

import com.queue.interceptor.InterceptorApplication;
import com.queue.interceptor.sender.MessageSender;
import com.queue.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("interceptor")
public class QueueListener {

    private static final Logger log = LoggerFactory.getLogger(com.queue.consumer.listener.QueueListener.class);

    @Autowired
    private MessageSender messageSender;

    @RabbitListener(queues = InterceptorApplication.FAKE_QUEUE_NAME)
    public void receiveMessage(final Message message) {
        log.info("Message intercepted ... ", message.toString());
        messageSender.sendMessage(message);
    }
}