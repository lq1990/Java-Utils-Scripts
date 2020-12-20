package rabbitmq.springboot.consumer.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.springboot.consumer.config.RabbitmqConfig;

/**
 * @author liuqiang
 * @since 2020-12-08 20:34
 */
@Component
public class ReceiveHandler {

    // to get msg from queue
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void send_email(String msg, Message message, Channel channel) {
        System.out.println("receive msg is: "+msg);
    }

}
