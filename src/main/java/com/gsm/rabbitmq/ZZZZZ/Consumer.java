package com.gsm.rabbitmq.ZZZZZ;

import com.gsm.rabbitmq.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者 使用 RabbitListener 监听队列
 */
@Component
public class Consumer {

    /**
     * 监听 email 队列
     * 可以监听多个队列
     */
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_NAME_EMAIL})
    public void receiveEmail(String msg, Message message, Channel channel) {
        System.out.println("email\t" + msg);
    }

    /**
     * 监听 message 队列
     */
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_NAME_MESSAGE})
    public void receiveMessage(String msg) {
        System.out.println("message\t" + msg);
    }
}
