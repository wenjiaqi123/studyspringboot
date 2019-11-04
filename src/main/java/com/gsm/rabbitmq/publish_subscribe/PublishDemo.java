package com.gsm.rabbitmq.publish_subscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 假设用户注册成功，我们用两种方式通知用户，【短信】【邮件】
 * 发布
 */
public class PublishDemo {
    //交换机
    private static final String EXCHANGE_NAME = "exchangeName";
    //短信队列名称
    private static final String QUEUE_MESSAGE = "messageQueue";
    //邮件队列名称
    private static final String QUEUE_EMAIL = "emailQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
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

        /**
         * 声明交换机
         * String 交换机名称
         * String 交换机类型
         *  类型四种
         *  FANOUT      对应 publish/subscribe 工作模式
         *  DIRECT      对应 Routing 工作模式
         *  TOPIC       对应 Topics 工作模式
         *  HEADERS     对应 Headers 工作模式
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        /**
         * 声明队列
         */
        channel.queueDeclare(QUEUE_MESSAGE, true, false, false, null);
        channel.queueDeclare(QUEUE_EMAIL, true, false, false, null);

        /**
         * 交换机与队列绑定
         * String 队列名称
         * String 交换机名称
         * String 路由Key，交换机根据这个key值将消息转发到 指定队列中，在发布/订阅模式中为空
         */
        channel.queueBind(QUEUE_MESSAGE,EXCHANGE_NAME,"");
        channel.queueBind(QUEUE_EMAIL,EXCHANGE_NAME,"");


        //准备发送的消息
        String message = "phoneNo=15211223344;email=123456@qq.com\t" + System.currentTimeMillis();
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("message = " + message);
    }
}