package own.stu.spring.rabbitmq;

import com.rabbitmq.client.QueueingConsumer;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by dell on 2017/3/22.
 */
public class _0_HelloWord extends Base {
    //消息队列名称
    private final static String QUEUE_NAME = "hello word";

    @Test
    public void send() throws IOException {
        //指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送的消息
        String message = "hello world!";
        //往队列中发出一条消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("Sent Message：'" + message + "'");
    }

    @Test
    public void receive() throws IOException, InterruptedException {
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received Message：'" + message + "'");
        }
    }
}
