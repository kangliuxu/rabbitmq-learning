package common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xkl
 * @date 2020/3/5
 * @description 模板类,建立连接和通道工作
 **/
public class RabbitTemplate {

    public static void execute(Consumer<Channel> consumer) throws Exception{
        //连接服务器(broker)
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.153.129");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            //将逻辑写到consumer
            consumer.accept(channel);
        }
    }

}
