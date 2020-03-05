package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import common.Constants;
import common.RabbitTemplate;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xkl
 * @date 2020/3/5
 * @description 生产者
 **/
public class NewTask{

    public static void main(String[] args) throws Exception {
        String message = String.join(" ",args);
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost("192.168.153.129");
        factory.setHost("118.25.65.132");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish("", Constants.QUEUE_NAME,null,message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }
}
