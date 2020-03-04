package workqueue;

import common.Constants;
import common.RabbitTemplate;

import java.io.IOException;

/**
 * @author xkl
 * @date 2020/3/5
 * @description 生产者
 **/
public class NewTask {
    public static void main(String[] args) throws Exception {
        String message = String.join(" ",args);
        RabbitTemplate.execute(channel->{
            try {
                channel.basicPublish("", Constants.QUEUE_NAME,null,message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
