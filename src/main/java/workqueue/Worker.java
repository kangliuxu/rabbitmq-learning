package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import common.Constants;

/**
 * @author xkl
 * @date 2020/3/5
 * @description
 **/
public class Worker{

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(Constants.TASK_QN, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //一时只消费一条消息
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(Constants.TASK_QN,autoAck,deliverCallback,consumerTag -> {});
    }

    private static void doWork(String task){
        for(char ch:task.toCharArray()){
            if(ch=='.'){
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
