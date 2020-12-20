package rabbitmq.routingKey;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * BuiltinExchangeType.DIRECT
 *
 *
 * @author liuqiang
 * @since 2020-12-01 20:05
 */
public class Producer_RoutingKey {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private static final String ROUTINGKEY_EMAIL = "inform_email";
    private static final String ROUTINGKEY_SMS = "inform_sms";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, "inform"); // a queue with many routingKeys
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, "inform");

            for (int i = 0; i < 5; i++) {
                String msg = "send email inform msg to user"+i;
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAIL, null, msg.getBytes());
                System.out.println("send to mq: " + msg+i);
            }
//
//            for (int i = 0; i < 3; i++) {
//                String msg = "send sms inform msg to user"+i;
//                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS, null, msg.getBytes());
//                // the published msg with routingKey
//                System.out.println("send to mq: " + msg+i);
//            }

            for (int i = 0; i < 2; i++) {
                String msg = "send inform msg to user"+i;
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "inform", null, msg.getBytes());
                // the published msg with routingKey
                System.out.println("send to mq: " + msg+i);
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null)
                    channel.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null)
                    connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
