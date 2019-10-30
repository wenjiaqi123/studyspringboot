package com.gsm.rabbitmq.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 入门程序
 * 消费者
 */
public class ConsumerDemo {

    private static final String QUEUE = "QueueName";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);
        //监听
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            /**
             * String 消费者标签，在 channel.basicConsume() 去指定
             * Envelope 消息包含的内容，可从中获取消息id，交换机，消息，和重传标志（收到消息失败后是否需要重新发送）
             * AMQP.BasicProperties 配置参数
             * byte[]  消息内容
             */
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("msg = " + msg);
            }
        };
        /**
         * 监听队列
         * String   队列名称
         * boolean  是否自动回复，设置为true，表示消息接收到自动向 mq回复接收到了，mq接收到回复会删除消息，设置为 false需要手动回复
         * Consumer 消费消息的方法，消费者接受到消息后调用此方法
         */
        channel.basicConsume(QUEUE, true, consumer);
    }
}
