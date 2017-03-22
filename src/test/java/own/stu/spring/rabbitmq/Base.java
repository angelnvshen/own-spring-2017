package own.stu.spring.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by dell on 2017/3/22.
 */
public class Base {

    protected Connection connection;

    protected Channel channel;

    @Before
    public void prepare() throws IOException, TimeoutException {
        /**
         * 创建连接连接到MabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("localhost");
        //指定用户 密码
        factory.setUsername("admin");
        factory.setPassword("admin");
        //指定端口
        factory.setPort(AMQP.PROTOCOL.PORT);
        //创建一个连接
        connection = factory.newConnection();
        //创建一个频道
        channel = connection.createChannel();
    }

    @After
    public void end() throws IOException, TimeoutException {
        if (channel != null)
            channel.close();
        if (connection != null)
            connection.close();
    }
}
