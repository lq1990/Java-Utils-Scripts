package rabbitmq.routingKey;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2020-12-01 20:05
 */
public class Consumer_RoutingKey_SMS {

    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
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

            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS);

            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    System.out.println("exchange: "+envelope.getExchange());
//                    System.out.println("tag: "+envelope.getDeliveryTag());
//                    System.out.println("routingKey: "+envelope.getRoutingKey());

                    String msg = new String(body, StandardCharsets.UTF_8);
                    System.out.println("receive sms: " +msg);
                }
            };

            channel.basicConsume(QUEUE_INFORM_SMS, true, defaultConsumer); // 2. autoAck, 3. callback


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
