package rabbitmq.subscribe_publish;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2020-11-30 20:36
 */
public class Consumer_SMS {
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";


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
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");


            // consume
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String exchange = envelope.getExchange();

                    long deliveryTag = envelope.getDeliveryTag();
                    String msg = new String(body, "utf-8");
                    System.out.println("receive sms: " +msg);
                }
            };

            /*
            1. queue name
            2. auto ack
            3. callback
             */
            channel.basicConsume(QUEUE_INFORM_SMS, true, defaultConsumer);


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }
}
