package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue orderQueue() {
        return new Queue("order-queue");
    }
    @Bean
    public Queue searchHistoryQueue() {
        return new Queue("searchHistory-queue");
    }
    @Bean
    public Queue cancelOrderQueue() {
        return QueueBuilder.durable("cancel-order-queue")
                .deadLetterExchange( "dead-letter-exchange")
                .deadLetterRoutingKey("overTime.order")
                .build();
    }
    @Bean
    public Queue deadLetterQueue() {
        return new Queue("dead-letter-queue");
    }


    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order-exchange");
    }
    @Bean
    public DirectExchange searchHistoryExchange() {
        return new DirectExchange("searchHistory-exchange");
    }
    @Bean
    public DirectExchange cancelOrderExchange() {
        return new DirectExchange("cancel-order-exchange");
    }
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dead-letter-exchange");
    }



    @Bean
    public Binding orderBinding(DirectExchange orderExchange, Queue orderQueue) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.create");
    }
    @Bean
    public Binding searchHistoryBinding(DirectExchange searchHistoryExchange, Queue searchHistoryQueue) {
        return BindingBuilder.bind(searchHistoryQueue).to(searchHistoryExchange).with("searchHistory.create");
    }

    @Bean
    public Binding cancelOrderBinding(DirectExchange cancelOrderExchange, Queue cancelOrderQueue) {
        return BindingBuilder.bind(cancelOrderQueue).to(cancelOrderExchange).with("cancel.order");
    }

    @Bean
    public Binding deadLetterBinding(DirectExchange deadLetterExchange, Queue deadLetterQueue){
        return  BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("overTime.order");
    }


    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate,"orderExchange","order.create");
    }

}
