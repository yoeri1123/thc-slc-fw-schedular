package shb.slc.amqp;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shb.slc.domain.SlcFWSchedularActvLogDomainQ;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class RabbitmqProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final static String MESSAGE_1 = "First Header Message Example";
    private final static String MESSAGE_2 = "Second Header Message Example";
    private final static String MESSAGE_3 = "Third Header Message Example";


    public void schedularActvLogDomainPublisher(SlcFWSchedularActvLogDomainQ domainQ){
        Map<String, Object> map = null;
        BasicProperties props = null;

        try {
            Connection conn = RabbitmqConnection.getConnection();

            if (conn != null) {
                Channel channel = conn.createChannel();
                String callbackQueue = channel.queueDeclare().getQueue();

                // First message
                map = new HashMap<String, Object>();
                map.put("First", "A");
                map.put("Fourth", "D");

                // BasicProperties setting
                props = new BasicProperties();
                props = props.builder().contentType("application/octet-stream").build();
                props = props.builder().messageId("rabbitmqMessageId").build();
                props = props.builder().appId("rabbitmqAppId").build();
                props = props.builder().userId("guest").build();
                props = props.builder()
                        .timestamp(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())).build();
                props = props.builder().headers(map).build();

                channel.basicPublish(RabbitmqHeaderExchange.EXCHANGE_NAME, RabbitmqHeaderExchange.ROUTING_KEY, props,
                        MESSAGE_1.getBytes());
                System.out.println("[Producer] Message Sent  '" + MESSAGE_1 + "'");

                // Second message
                map = new HashMap<String, Object>();
                map.put("Third", "C");

                // BasicProperties setting
                props = new BasicProperties();
                props = props.builder().headers(map).build();

                channel.basicPublish(RabbitmqHeaderExchange.EXCHANGE_NAME, RabbitmqHeaderExchange.ROUTING_KEY, props,
                        MESSAGE_2.getBytes());
                System.out.println("[Producer] Message Sent  '" + MESSAGE_2 + "'");

                // Third message
                map = new HashMap<String, Object>();
                map.put("First", "A");
                map.put("Third", "C");

                String corrID = UUID.randomUUID().toString();

                props = new BasicProperties();
                props = props.builder().contentType("application/octet-stream").build();
                props = props.builder().messageId("rabbitmqMessageId").build();
                props = props.builder().appId("rabbitmqAppId").build();
                props = props.builder().replyTo(callbackQueue).build();
                props = props.builder().correlationId(corrID).build();
                props = props.builder().userId("guest").build();
                props = props.builder()
                        .timestamp(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())).build();
                props = props.builder().headers(map).build();
                props = props.builder().headers(map).build();

                channel.basicPublish(RabbitmqHeaderExchange.EXCHANGE_NAME, RabbitmqHeaderExchange.ROUTING_KEY, props,
                        MESSAGE_3.getBytes());
                System.out.println("[Producer] Message Sent  '" + MESSAGE_3 + "'");

                final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

                System.out.println("1");
                channel.basicConsume(callbackQueue, true, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        System.out.println("2");
                        try{
                            if(corrID.equals(properties.getCorrelationId())) response.offer(new String(body, "UTF-8"));
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                });

                System.out.println("[Producer] Call Back Message : " + response.take());


                System.out.println("\n----------------------------------------------------------------------------------------------\n");
                // channel.close();
                // conn.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
