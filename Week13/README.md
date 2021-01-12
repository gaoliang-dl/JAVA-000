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
### 启动zookeeper
```bash
mv conf/zoo_sample.cfg conf/zoo.cfg
vim conf/zoo.cfg
mkdir data
dataDir=/Users/a/Downloads/apache-zookeeper-3.6.2-bin/data
bin/zkServer.sh start
bin/zkCli.sh -server 127.0.0.1:2181
```
```bash
[zk: 127.0.0.1:2181(CONNECTED) 0] ls /
[zookeeper]
[zk: 127.0.0.1:2181(CONNECTED) 1] ls /zookeeper 
[config, quota]
[zk: 127.0.0.1:2181(CONNECTED) 2] 
```
```bash
a                12095   0.1  0.5  7011704  86320 s000  S     8:52下午   0:01.62 /usr/bin/java -Dzookeeper.log.dir=/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../logs -Dzookeeper.log.file=zookeeper-a-server-adeMacBook-Pro.local.log -Dzookeeper.root.logger=INFO,CONSOLE -XX:+HeapDumpOnOutOfMemoryError -XX:OnOutOfMemoryError=kill -9 %p -cp /Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../zookeeper-server/target/classes:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../build/classes:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../zookeeper-server/target/lib/*.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../build/lib/*.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/zookeeper-prometheus-metrics-3.6.2.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/zookeeper-jute-3.6.2.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/zookeeper-3.6.2.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/snappy-java-1.1.7.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/slf4j-log4j12-1.7.25.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/slf4j-api-1.7.25.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/simpleclient_servlet-0.6.0.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/simpleclient_hotspot-0.6.0.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/simpleclient_common-0.6.0.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/simpleclient-0.6.0.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-transport-native-unix-common-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-transport-native-epoll-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-transport-4.1.50.Final.jar:/Users/a/Downloads/apachezookeeper-3.6.2-bin/bin/../lib/netty-resolver-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-handler-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-common-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-codec-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/netty-buffer-4.1.50.Final.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/metrics-core-3.2.5.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/log4j-1.2.17.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/json-simple-1.1.1.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jline-2.14.6.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jetty-util-9.4.24.v20191120.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jetty-servlet-9.4.24.v20191120.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jetty-server-9.4.24.v20191120.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jetty-security-9.4.24.v20191120.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jetty-io-9.4.24.v20191120.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jetty-http-9.4.24.v20191120.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/javax.servlet-api-3.1.0.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jackson-databind-2.10.3.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jackson-core-2.10.3.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/jackson-annotations-2.10.3.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/commons-lang-2.6.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/commons-cli-1.2.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../lib/audience-annotations-0.5.0.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../zookeeper-*.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../zookeeper-server/src/main/resources/lib/*.jar:/Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../conf: -Xmx1000m -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false org.apache.zookeeper.server.quorum.QuorumPeerMain /Users/a/Downloads/apache-zookeeper-3.6.2-bin/bin/../conf/zoo.cfg
```
### 启动kafka
```bash
vim config/server.properties
listeners=PLAINTEXT://localhost:9092
bin/kafka-server-start.sh config/server.properties 
```
```bash
[zk: 127.0.0.1:2181(CONNECTED) 4] ls /
[admin, brokers, cluster, config, consumers, controller, controller_epoch, feature, isr_change_notification, latest_producer_id_block, log_dir_event_notification, zookeeper]
```
