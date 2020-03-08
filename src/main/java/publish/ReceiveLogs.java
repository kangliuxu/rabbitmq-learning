package publish;

import com.rabbitmq.client.*;
import common.Constants;

/**
 * @author xkl
 * @date 2020/3/7
 * @description
 **/
public class ReceiveLogs {
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constants.LOGS_EXCHANGE,"fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,Constants.LOGS_EXCHANGE,"");

        System.out.println("[*] Waiting for message .To Exit  press CTRL+C ");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(queueName,true,callback,consumerTak->{});

    }
}
