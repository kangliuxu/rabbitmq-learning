package workqueue;

import com.rabbitmq.client.DeliverCallback;
import common.Constants;
import common.RabbitTemplate;

import java.io.IOException;

/**
 * @author xkl
 * @date 2020/3/5
 * @description
 **/
public class Worker {

    public static void main(String[] args) throws Exception {
        RabbitTemplate.execute(channel -> {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            };

            boolean autoAck = true;
            try {
                channel.basicConsume(Constants.QUEUE_NAME,autoAck,deliverCallback,consumerTag -> {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void doWork(String task) throws InterruptedException {
        for(char ch:task.toCharArray()){
            if(ch=='.')
                Thread.sleep(1000);
        }
    }
}
