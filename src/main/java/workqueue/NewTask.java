package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import common.Constants;

/**
 * @author xkl
 * @date 2020/3/5
 * @description 生产者
 **/
public class NewTask{

    public static void main(String[] args) throws Exception {
        String message = String.join(" ",args);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            boolean durable = true;
            channel.queueDeclare(Constants.TASK_QN,durable,false,false,null);

            channel.basicPublish("", Constants.TASK_QN, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
