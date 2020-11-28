package rabbitmq.basic_workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2020-11-28 19:49
 */
public class Consumer02 {
    private static final String QUEUE = "helloworld";


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.101.123");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        connectionFactory.setVirtualHost("/");

        Connection connection = null;

        try {
            connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            // declare queue
            channel.queueDeclare(QUEUE, true, false, false, null);

            // listen queue
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                // if consumer get message, this fn would be called

                /**
                 *
                 * @param consumerTag tag of consumer, in order to tag consumer.
                 * @param envelope
                 * @param properties
                 * @param body
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String exchange = envelope.getExchange();
                    long deliveryTag = envelope.getDeliveryTag(); // identify message, used to confirm the msg is already got by consumer

                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("exchange: "+exchange);
                    System.out.println("tag: "+deliveryTag);
                    System.out.println("receive message: "+message);

                }
            };
            /*
            1. name of queue
            2. autoAck: automatic response. if true, then auto response. if false, we should program
            3. consume callback: if consumer get the message, the consumer should exe a fn
             */
            channel.basicConsume(QUEUE, true, defaultConsumer);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            // consumer needs to listen queue always, so do not need close
        }


    }
}
