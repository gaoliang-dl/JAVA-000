package io.kimmking.javacourse.mq.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ActivemqApplication {

    public static void main(String[] args) {
        // 定义Destination
        Destination destination = new ActiveMQTopic("gaol.test.topic");
//         Destination destination = new ActiveMQQueue("gaol.test.queue");
        testDestination(destination);
    }

    public static void testDestination(Destination destination) {
        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建消费者
            MessageConsumer consumer = session.createConsumer( destination );
            final AtomicInteger count = new AtomicInteger(0);
            consumer.setMessageListener(message -> {
                System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);
            });

            // 创建生产者，生产10个消息
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage(i + " message.");
                producer.send(message);
            }

            Thread.sleep(2000);
            session.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
