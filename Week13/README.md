# 第 24 课作业实践
## 1、(必做)搭建ActiveMQ服务，基于JMS，写代码分别实现对于queue和topic的消息 生产和消费，代码提交到github。
### 启动ActiveMQ
```bash
./activemq console
http://127.0.0.1:8161/admin/
Login: admin
Passwort: admin
```
### SRC
#### queue
##### 连接
```java
            Destination destination = new ActiveMQQueue("gaol.test.queue");
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
```
##### 生产
```java
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage(i + " message.");
                producer.send(message);
            }
```
##### 消费
```java
            MessageConsumer consumer = session.createConsumer( destination );
            final AtomicInteger count = new AtomicInteger(0);
            consumer.setMessageListener(message -> {
                System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);
            });
```
##### log
```txt
1 => receive from queue://gaol.test.queue: ActiveMQTextMessage {commandId = 6, responseRequired = true, messageId = ID:adeMacBook-Pro.local-54731-1610291012573-1:1:1:1:1, originalDestination = null, originalTransactionId = null, producerId = ID:adeMacBook-Pro.local-54731-1610291012573-1:1:1:1, destination = queue://gaol.test.queue, transactionId = null, expiration = 0, timestamp = 1610291012764, arrival = 0, brokerInTime = 1610291012765, brokerOutTime = 1610291012767, correlationId = null, replyTo = null, persistent = true, type = null, priority = 4, groupID = null, groupSequence = 0, targetConsumerId = null, compressed = false, userID = null, content = org.apache.activemq.util.ByteSequence@77abd83, marshalledProperties = null, dataStructure = null, redeliveryCounter = 0, size = 0, properties = null, readOnlyProperties = true, readOnlyBody = true, droppable = false, jmsXGroupFirstForConsumer = false, text = 0 message.}
```
#### topic
##### 连接
```java
            Destination destination = new ActiveMQTopic("gaol.test.topic");
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
```
##### 生产
```java
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage(i + " message.");
                producer.send(message);
            }
```
##### 消费
```java
            MessageConsumer consumer = session.createConsumer( destination );
            final AtomicInteger count = new AtomicInteger(0);
            consumer.setMessageListener(message -> {
                System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);
            });
```
##### log
```txt
1 => receive from topic://gaol.test.topic: ActiveMQTextMessage {commandId = 6, responseRequired = true, messageId = ID:adeMacBook-Pro.local-54757-1610291158471-1:1:1:1:1, originalDestination = null, originalTransactionId = null, producerId = ID:adeMacBook-Pro.local-54757-1610291158471-1:1:1:1, destination = topic://gaol.test.topic, transactionId = null, expiration = 0, timestamp = 1610291158678, arrival = 0, brokerInTime = 1610291158680, brokerOutTime = 1610291158680, correlationId = null, replyTo = null, persistent = true, type = null, priority = 4, groupID = null, groupSequence = 0, targetConsumerId = null, compressed = false, userID = null, content = org.apache.activemq.util.ByteSequence@547ba999, marshalledProperties = null, dataStructure = null, redeliveryCounter = 0, size = 0, properties = null, readOnlyProperties = true, readOnlyBody = true, droppable = false, jmsXGroupFirstForConsumer = false, text = 0 message.}
```
#### include
```java
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
```
```xml
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-all</artifactId>
            <version>5.15.0</version>
        </dependency>
```
# 第 25 课作业实践
## 1、(必做)搭建一个3节点Kafka集群，测试功能和性能;实现spring kafka下对kafka集群 的操作，将代码提交到github。
