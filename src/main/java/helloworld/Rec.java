package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author xkl
 * @date 2020/3/4
 * @description 接收者,接收者需要一直监听消息
 **/
public class Rec {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.153.129");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null );
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //回调
        DeliverCallback deliverCallback = (cosumerTag,delivery) -> {
            String message = new String(delivery.getBody(), Charset.defaultCharset());
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,comsumerTag ->{});
    }
}
