package own.stu.spring.rabbitmq;

import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by dell on 2017/3/22.
 */
public class _1_workQueue extends Base {

    //队列名称
    private final static String QUEUE_NAME = "workqueue-durable";

    @Test
    public void task() throws IOException {
        //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        boolean durable = true;

        //声明队列
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 10; i > 0; i--) {
            String dots = "";
            for (int j = 0; j <= i; j++) {
                dots += ".";
            }
            String message = "hello world" + dots + dots.length();
            //MessageProperties.PERSISTENT_TEXT_PLAIN 标识我们的信息为持久化的
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("Sent Message：'" + message + "'");
        }
    }

    @Test
    public void work() throws IOException, InterruptedException {
        boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        //声明队列
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);

        /**
         * ack= true: Round-robin 转发   消费者被杀死，消息会丢失
         * ack=false:消息应答 ，为了保证消息永远不会丢失，RabbitMQ支持消息应答（message acknowledgments）。
         * 消费者发送应答给RabbitMQ，告诉它信息已经被接收和处理，然后RabbitMQ可以自由的进行信息删除。
         * 如果消费者被杀死而没有发送应答，RabbitMQ会认为该信息没有被完全的处理，然后将会重新转发给别的消费者。
         * 通过这种方式，你可以确认信息不会被丢失，即使消者偶尔被杀死。
         * 消费者需要耗费特别特别长的时间是允许的。
         *
         */
        boolean ack = false; //打开应答机制
        // 指定消费队列
        channel.basicConsume(QUEUE_NAME, ack, consumer);

        //公平转发  设置最大服务转发消息数量    只有在消费者空闲的时候会发送下一条信息。
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            String message = new String(delivery.getBody());

            System.out.println(hashCode() + " Received Message：'" + message + "'");

            doWork(message);
            System.out.println(hashCode() + " Received Done");
            //发送应答
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        }
    }

    /**
     * 每个点耗时1s
     *
     * @param task
     * @throws InterruptedException
     */
    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.')
                Thread.sleep(1000);
        }
    }
}
