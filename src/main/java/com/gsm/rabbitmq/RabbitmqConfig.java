package com.gsm.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

//@Configuration
public class RabbitmqConfig {
    //队列名称 email
    public static final String QUEUE_NAME_EMAIL = "queueNameEmail";
    //队列名称 message
    public static final String QUEUE_NAME_MESSAGE = "queueNameMESSAGE";
    //交换机名称
    public static final String EXCHANGE_NAME_TOPICS = "exchangeNameTopics";
    //routingKey email
    public static final String ROUTING_KEY_EMAIL = "key.#.email.#";
    //routingKey message
    public static final String ROUTING_KEY_MESSAGE = "key.#.message.#";

    /**
     * 声明交换机，交换机名称
     * ExchangeBuilder 提供了 fanout(发布订阅)  direct(路由key)  topic(模糊路由key)  header 四种类型交换机的配置
     * @return
     */
    @Bean(EXCHANGE_NAME_TOPICS)
    public Exchange getExchangeTopics(){
        //durable(true) 持久化，消息队列重启后交换器仍然存在
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME_TOPICS).durable(true).build();
    }

    /**
     * 声明队列，队列名称
     * @return  Email 队列
     */
    @Bean(QUEUE_NAME_EMAIL)
    public Queue getQueueEmail(){
        Queue queue = new Queue(QUEUE_NAME_EMAIL);
        return queue;
    }

    /**
     * 声明队列，队列名称
     * @return  Message 队列
     */
    @Bean(QUEUE_NAME_MESSAGE)
    public Queue getQueueMessage(){
        Queue queue = new Queue(QUEUE_NAME_MESSAGE);
        return queue;
    }

    /**
     * 交换机 + 队列 绑定
     * @param queue Email 队列
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindExchangeQueueEmail(@Qualifier(QUEUE_NAME_EMAIL) Queue queue,@Qualifier(EXCHANGE_NAME_TOPICS) Exchange exchange){
        //绑定建造者模式 绑定队列，到交换机，使用规则
        Binding noargs = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
        return noargs;
    }

    /**
     * 交换机 + 队列 绑定
     * @param queue Message 队列
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindExchangeQueueMessage(@Qualifier(QUEUE_NAME_MESSAGE) Queue queue,@Qualifier(EXCHANGE_NAME_TOPICS) Exchange exchange){
        //绑定建造者模式 绑定队列，到交换机，使用规则
        Binding noargs = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MESSAGE).noargs();
        return noargs;
    }
}
