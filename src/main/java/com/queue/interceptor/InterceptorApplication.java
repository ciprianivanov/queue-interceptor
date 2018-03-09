package com.queue.interceptor;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@SpringBootApplication
@EnableRabbit
@Configuration
@Profile("interceptor")
public class InterceptorApplication implements RabbitListenerConfigurer {

    //TODO: Move to .properties file
    public final static String EXCHANGE_NAME = "Custom-Exchange";
    public final static String REAL_QUEUE_NAME = "Real-Queue";
    public final static String FAKE_QUEUE_NAME = "Fake-Queue";
    public final static String REAL_ROUTING_KEY = "real";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue realAppQueue() {
        return new Queue(REAL_QUEUE_NAME);
    }

    @Bean
    public Queue fakeAppQueue() {
        return new Queue(FAKE_QUEUE_NAME);
    }


    @Bean
    public Binding declareBindingReal() {
        return BindingBuilder.bind(realAppQueue()).to(appExchange()).with(REAL_ROUTING_KEY);
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        return new DefaultMessageHandlerMethodFactory();
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}