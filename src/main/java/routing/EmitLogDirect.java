package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import common.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xkl
 * @date 2020/3/7
 * @description 绑定键的直连方式
 **/
public class EmitLogDirect {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            ){

            //生命一个direct 的交换机
            channel.exchangeDeclare(Constants.DIRECT_EXCHANGE,"direct");
            String severity = getSeverity(args);
            String message = getMessage(args);

            channel.basicPublish(Constants.DIRECT_EXCHANGE,severity,null,message.getBytes());
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        }

    }

    private static String getSeverity(String[] strings) {
        if (strings.length < 1)
            return "info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length <= startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
