package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import common.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xkl
 * @date 2020/3/7
 * @description
 **/
public class RecieveLogDirect {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constants.DIRECT_EXCHANGE, "direct");
        String queueName = channel.queueDeclare().getQueue();

        if(args.length<1){
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        for(String severity:args){
            channel.queueBind(queueName,Constants.DIRECT_EXCHANGE,severity);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("[x] Received "+delivery.getEnvelope().getRoutingKey()+":"+message);
        };

        channel.basicConsume(queueName,true,callback,consumerTag -> {});

    }

}
