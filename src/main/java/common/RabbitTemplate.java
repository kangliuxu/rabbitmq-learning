package common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xkl
 * @date 2020/3/5
 * @description 模板类,建立连接和通道工作
 **/
public abstract class RabbitTemplate {

    public void execute() throws Exception{
        //连接服务器(broker)
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost("192.168.153.129");
        factory.setHost("118.25.65.132");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //将逻辑写到consumer
        process(channel);
    }

    protected abstract void process(Channel channel) throws IOException;


}
