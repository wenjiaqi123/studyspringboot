package com.gsm.rabbitmq.publish_subscribe;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅,邮件订阅
 */
public class SubscribeEmailDemo {
    //交换机
    private static final String EXCHANGE_NAME = "exchangeName";
    //邮件队列名称
    private static final String QUEUE_EMAIL = "emailQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        //创建连接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //通道信息
        channel.queueDeclare(QUEUE_EMAIL, true, false, false, null);

        //交换机与队列绑定
        channel.queueBind(QUEUE_EMAIL,EXCHANGE_NAME,"");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 消息内容
                String msg = new String(body, "UTF-8");
                System.out.println("msg = " + msg);
            }
        };

        channel.basicConsume(QUEUE_EMAIL, true, consumer);
    }
}