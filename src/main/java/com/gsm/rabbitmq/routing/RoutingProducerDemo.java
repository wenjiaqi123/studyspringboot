package com.gsm.rabbitmq.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Routing 工作模式 生产者
 */
public class RoutingProducerDemo {
    //交换机名称
    public static final String EXCHANGE_NAME = "exchangeNameRouting";
    //短信队列名称
    private static final String QUEUE_MESSAGE = "messageQueueRouting";
    //邮件队列名称
    private static final String QUEUE_EMAIL = "emailQueueRouting";
    //RoutingKey 短信
    private static final String ROUTING_KEY_MESSAGE = "message";
    //RoutingKey 邮件
    private static final String ROUTING_KEY_EMAIL = "email";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        //根据工厂创建连接
        Connection connection = factory.newConnection();
        //根据连接创建通道
        Channel channel = connection.createChannel();

        /**
         * 声明交换机
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        /**
         * 声明队列
         */
        channel.queueDeclare(QUEUE_MESSAGE,true,false,false,null);
        channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);

        /**
         * 队列和交换机绑定
         * (队列名称,交换机名称,key)
         */
        channel.queueBind(QUEUE_MESSAGE,EXCHANGE_NAME,ROUTING_KEY_MESSAGE);
        channel.queueBind(QUEUE_EMAIL,EXCHANGE_NAME,ROUTING_KEY_EMAIL);

        //-------------上面是准备工作,我是华丽丽的分割线----------------

        String msg = "其实消息队列也很简单的嘛\t" + System.currentTimeMillis();

        //TODO 切换RoutingKey名称 ROUTING_KEY_MESSAGE
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_MESSAGE,null,msg.getBytes());
        System.out.println("msg = " + msg);
    }
}
