package rabbitmq.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rabbitmq.springboot.producer.RunRabbitmqApplication;
import rabbitmq.springboot.producer.config.RabbitmqConfig;

/**
 * @author liuqiang
 * @since 2020-12-05 17:51
 */
@SpringBootTest(classes = RunRabbitmqApplication.class)
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    RabbitTemplate rabbitTemplate; // use this template to send msg

    @Test
    public void test() {
        System.out.println("test");
    }

    @Test
    public void testSendByTopics() {
        for (int i = 0; i < 5; i++) {
            String msg = "sms email inform to user"+i;

            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", msg);
            System.out.println("send msg is: "+msg);
        }
    }

}
