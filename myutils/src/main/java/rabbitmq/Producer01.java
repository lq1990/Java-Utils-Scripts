package rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liuqiang
 * @since 2020-11-27 20:40
 */
public class Producer01 {

    private static final String QUEUE = "helloworld";

    public static void main(String[] args) {
        // 1. create connection factory, and use it to connect with mq
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.101.123");
        connectionFactory.setPort(5672);
        // 15672 is for webui, 5672 is for connection

        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        // set virtual host, we can set many virtual hosts, each virtual host is an independent MQ.
        // that means: we do not need to install many MQs, since we have many virtual hosts
        connectionFactory.setVirtualHost("/");

        try {
            // connect
            Connection connection = connectionFactory.newConnection();

            // channel, producer -> mq through channel
            Channel channel = connection.createChannel();

            // declare queue
            /*
             * params:
             * 1. name of queue
             * 2. durable, whether exits after restarting queue
             * 3. exclusive, whether only allow the conn in a certain queue. If close the conn, the queue would be deleted
             *      if true, a temp queue can be created, because after closing the conn, this queue would be deleted.
             * 4. autoDelete, if auto deleted. also can be used to create a temp queue.
             * 5. arguments, extended args, e.g. lifespan
             *
             */
            channel.queueDeclare(QUEUE, true, false, false, null);

            // send msg
            /*
            1. exchange. default as mq's exchange
            2. routingKey: if use default exchange, routingKey should be the name of queue
            3. props
            4. body: important!!!
             */
            String msg = "hello world, i am lq";
            channel.basicPublish("", QUEUE, null, msg.getBytes());

            System.out.println("send to mq, over");


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            // close conn
        }


    }
}
