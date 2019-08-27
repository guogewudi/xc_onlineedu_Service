package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/26
 * Description:
 */
public class Product01 {
    private static final String QUEUE= "helloworld";
    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接 连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机  一个mq服务可以设置多个虚拟机 每个虚拟机相当于多个mq
        connectionFactory.setVirtualHost("/");
        //建立新链接
        Connection connection = null;
        Channel channel = null;
        try {
           connection = connectionFactory.newConnection();
           //设置会话通道
            //生产者 mq服务都在通道中完成
            channel = connection.createChannel();
            //声明队列String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            /*
                1 队列名称helloworld 没有则创建
                2是否持久化？持久化mq 重启后队列还在
                3是否独占连接 队列只允许该链接访问 连接关闭队列自动删除 true可以用于临时队列的创建
                4 自动删除
                5.参数？设置一个队列的扩展参数
                */
            channel.queueDeclare(QUEUE,true,false,false,null);
            //发送消息
            /*
            1交换机  不指定则用默认交换机  ""
            2路由key  交换机根据路由key将消息转发到指定队列 默认交换机，设置为队列名称
            3额外的消息的属性
            4消息内容
             */
            //消息内容
            String message = "你好啊sb";
            channel.basicPublish("",QUEUE,null,message.getBytes());
            System.out.println("send to mq"+message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if(channel!= null)
            {
                channel.close();
            }
            if(connection != null)
            {
                connection.close();
            }

        }

    }
}
