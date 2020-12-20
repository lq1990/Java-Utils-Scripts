package rabbitmq.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2020-12-02 20:33
 */
public class Producer_Topics {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";
    private static final String ROUTINGKEY_EMAIL = "inform.#.email.#";
    private static final String ROUTINGKEY_SMS = "inform.#.sms.#";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
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
            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);

            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPICS_INFORM, ROUTINGKEY_EMAIL); // 3. routingKey
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPICS_INFORM, ROUTINGKEY_SMS); // 3. routingKey

            for (int i = 0; i < 4; i++) {
                String msg = "send email msg to user: "+ i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.email", null, msg.getBytes());
            }

            for (int i = 0; i < 2; i++) {
                String msg = "send sms msg to user: "+ i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.sms", null, msg.getBytes());
            }

            for (int i = 0; i < 1; i++) {
                String msg = "send email/sms msg to user: "+ i;
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.email.sms", null, msg.getBytes());
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
