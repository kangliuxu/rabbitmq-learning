package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import common.Constants;
import common.RabbitTemplate;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xkl
 * @date 2020/3/5
 * @description
 **/
public class Worker extends RabbitTemplate{

    public static void main(String[] args) throws Exception {
       new Worker().execute();
    }

    @Override
    protected void process(Channel channel) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        boolean autoAck = true;
        channel.basicConsume(Constants.QUEUE_NAME,autoAck,deliverCallback,consumerTag -> {});
    }

    private static void doWork(String task) throws InterruptedException {
        for(char ch:task.toCharArray()){
            if(ch=='.'){
                Thread.sleep(1000);
            }
        }
    }
}
