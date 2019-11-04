package com.gsm.rabbitmq.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Routing 模式 消费者 Message邮件
 */
public class RoutingConsumerMessageDemo {
    //交换机名称
    public static final String EXCHANGE_NAME = "exchangeNameRouting";
    //短信队列名称
    private static final String QUEUE_MESSAGE = "messageQueueRouting";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明队列
        channel.queueDeclare(QUEUE_MESSAGE,true,false,false,null);
        //队列绑定交换机
        channel.queueBind(QUEUE_MESSAGE,EXCHANGE_NAME,"");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("msg = " + msg);
            }
        };

        channel.basicConsume(QUEUE_MESSAGE, true, consumer);
    }
}
