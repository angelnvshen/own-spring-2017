package own.stu.spring.rabbitmq;

import com.rabbitmq.client.QueueingConsumer;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by dell on 2017/3/22.
 */
public class _3_route extends Base {

    //交换名称
    private static final String EXCHANGE_NAME = "ex_logs_direct";

    //日志分类
    private static final String[] SEVERITIES = {"info", "warning", "error"};

    /**
     * 随机发送6条随机类型（routing key）的日志给转发器
     */
    @Test
    public void sendLogDirect() throws IOException {
        // 声明转发器的类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //发送6条消息
        for (int i = 0; i < 6; i++) {
            String severity = getSeverity();
            String message = severity + "_log :" + UUID.randomUUID().toString();

            // 发布消息至转发器，指定routingkey
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

    /**
     * 接收端随机设置一个日志严重级别（binding_key）
     */
    @Test
    public void ReceiveLogsDirect() throws IOException, InterruptedException {

        // 声明direct类型转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String queueName = channel.queueDeclare().getQueue();
        String severity = getSeverity();

        // 指定binding_key
        channel.queueBind(queueName, EXCHANGE_NAME, severity);
        System.out.println(" [*] Waiting for " + severity + " logs. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
        }
    }


    /**
     * 随机产生一种日志类型
     *
     * @return
     */
    private static String getSeverity() {
        Random random = new Random();
        int ranVal = random.nextInt(3);
        return SEVERITIES[ranVal];
    }
}
