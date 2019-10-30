package com.gsm.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 入门程序
 * 生产者
 */
public class ProducerDemo {

    private static final String QUEUQ = "QueueName";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂 ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        //设置 ip 端口 用户名 用户密码 虚拟机
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");

        //根据工厂创建 连接
        Connection connection = factory.newConnection();
        //根据连接创建 通道
        Channel channel = connection.createChannel();

        //设置通道的属性
        /**
         * String 队列名称
         * boolean 是否持久化
         * boolean 队列是否独占此连接
         * boolean 队列不使用时是否自动删除此队列
         * Map 队列参数
         */
        channel.queueDeclare(QUEUQ, true, false, false, null);

        //准备发送的信息
        String message = "helloworld家奇" + System.currentTimeMillis();

        //通道发送信息
        /**
         * String Exchange的名称，如果没有，默认是 Default Exchange
         * String 队列名称，交换机发送消息到该消息队列
         * BasicProperties 消息包含的属性
         * byte[] 消息的字节数组
         */
        channel.basicPublish("", QUEUQ, null, message.getBytes());

        System.out.println("message = " + message);
    }
}
