package publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import common.Constants;


/**
 * @author xkl
 * @date 2020/3/7
 * @description 指定exchange 的广播式  将消息发送给exchange 实现订阅者方式
 **/
public class EmitLog {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constants.LOGS_EXCHANGE,"fanout");
        String message = args.length<1 ? "info: Hello World": String.join(" ",args);
        channel.basicPublish(Constants.LOGS_EXCHANGE,"",null,message.getBytes("UTF-8"));
        System.out.println("[x] sent '"+message +"'");

    }
}
