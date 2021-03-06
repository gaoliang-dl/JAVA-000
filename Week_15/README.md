# 毕业总结  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_15/%E6%AF%95%E4%B8%9A%E6%80%BB%E7%BB%93.svg)   

# 1.    JVM
学习了这部分在，在现实项目中得以应用：首先，不在简单地java -jar启动spring boot包了，现在加上了xms和xmx，更尝试使用了G1 GC；其次，尝试用java命令行工具去分析生产中遇到的内存问题，并尝试将生产jvm dump出来，在本地机器使用可视化工具分析。

## 1.1.     字节码

## 1.2.     线程栈

## 1.3.     方法调用指令

## 1.4.     类加载器

## 1.5.     内存模型

### 1.5.1.     栈

#### 1.5.1.1.      线程栈

##### 1.5.1.1.1.       帧

### 1.5.2.     堆

#### 1.5.2.1.      年轻代

##### 1.5.2.1.1.       Eden

###### 1.5.2.1.1.1.       TLAB

##### 1.5.2.1.2.       S0

##### 1.5.2.1.3.       S1

#### 1.5.2.2.      老年代

### 1.5.3.     非堆

#### 1.5.3.1.      元数据

##### 1.5.3.1.1.       方法区

###### 1.5.3.1.1.1.       常量池

#### 1.5.3.2.      压缩类空间

#### 1.5.3.3.      代码缓存

## 1.6.     内存屏障

## 1.7.     启动参数

## 1.8.     核心工具

## 1.9.     GC

## 1.10.   调优

# 2.    nio
这部分内容在生产中，没有直接用到，都是间接调用Netty等，API网关也只是使用了和Java无关的NGinx；但是我有了性能意识，如果用了某个组件，它使用了Netty或NIO，那么它的性能大概率是会好一些的；比如：Zuul不行，Redis 6.0会快很多。

## 2.1.     5种IO模型

## 2.2.     Netty

### 2.2.1.     原理

### 2.2.2.     优化

## 2.3.     API网关

# 3.    并发编程
这个块是精华，也是我掌握比较好，对显示项目用处最大的一块。最近负责的一个签约项目，CPU密集性，需要控制线程数，而且某些线程还有相关性，我快速第实现了Demo，用了线程池、Future、CountDownLatch等技术；虽然最后弃用了此方案，但快速Demo也让方案评判有了抓手。

## 3.1.     多线程

## 3.2.     线程安全

### 3.2.1.     利器

#### 3.2.1.1.      ThreadLocal

#### 3.2.1.2.      并行Stream

## 3.3.     线程池

## 3.4.     接口

### 3.4.1.     Callable

### 3.4.2.     Future

## 3.5.     并发包JUC

### 3.5.1.     锁

### 3.5.2.     并发原子类

### 3.5.3.     并发工具类

#### 3.5.3.1.      AQS

### 3.5.4.     线程安全集合类

# 4.    Spring和ORM等框架
Spring很少用，太麻烦，SpringBoot必用，简单。AOP和事务也常用。ORM只用过MyBatis或MyBatisPlus，JPA/Hibernate没用过。这块内容掌握的不太好，还需要后续深入学习。至于Lambda偶尔会在实战中使用，感觉很酷，但还不熟练。Lombak常用，但Guava没用过。单体测试也开始引入了，但项目时间紧，测试用例只能写一点关键用例。去年还在用JUnit4，今年已使用JUnit5了；Spock Groovy没感尝试。

## 4.1.     Spring Framework

## 4.2.     AOP

## 4.3.     IoC

### 4.3.1.     DI

## 4.4.     Spring Bean

## 4.5.     Spring XML

## 4.6.     Spring Messaging

## 4.7.     Spring Boot

### 4.7.1.     约定大于配置

### 4.7.2.     自动化配置

### 4.7.3.     Starter

## 4.8.     Hibernate

## 4.9.     MyBatis

## 4.10.   JPA

## 4.11.   Spring事务管理

## 4.12.   Java8 Lambda

## 4.13.   Java8 Stream

## 4.14.   Lombok

## 4.15.   Guava

## 4.16.   其他

### 4.16.1.   设计原则

### 4.16.2.   设计模式

### 4.16.3.   单元测试

# 5.    MySQL数据库和SQL
MySQL数据库是显示项目中必有的一环。通过学习我也在实战中优化了项目的MySQL使用。之前只知道适用MySQL默认隔离方式，但按照课程里讲了，发现Update时大量锁了间隙锁，Insert只能等；而这种等是不必要的。后来我将生产的隔离级别从可重复读改为了读已提交，性能大升。以前设计DB表，感觉没什么抓手，这样也行，那样也行，有了设计范式的概念，至少有了方法论。

## 5.1.     数据库设计范式

## 5.2.     常见关系型数据库

## 5.3.     SQL

### 5.3.1.     DQL

### 5.3.2.     DML

### 5.3.3.     TCL

### 5.3.4.     DCL

### 5.3.5.     DDL

### 5.3.6.     CCL

## 5.4.     MySQL

### 5.4.1.     存储

### 5.4.2.     执行流程

### 5.4.3.     引擎

### 5.4.4.     状态

### 5.4.5.     SQL执行顺序

### 5.4.6.     索引原理

### 5.4.7.     优化

#### 5.4.7.1.      参数配置优化

#### 5.4.7.2.      数据库设计优化

#### 5.4.7.3.      SQL优化

#### 5.4.7.4.      主键ID

#### 5.4.7.5.      高效分页

#### 5.4.7.6.      乐观锁与悲观锁

### 5.4.8.     事务和锁

### 5.4.9.     锁

### 5.4.10.   高可用

#### 5.4.10.1.    主从复制

#### 5.4.10.2.    读写分离

#### 5.4.10.3.    主从手动切换

#### 5.4.10.4.    MHA

#### 5.4.10.5.    MGR

#### 5.4.10.6.    MySQL CLuster

#### 5.4.10.7.    Orchestrator

# 6.    分库分表
小公司，没有在实战中用过分库分表，但在前年就已关注到了ShardingSphere，没研究过；最近的签约项目考虑到未来的数据量，引入了ShardingSphere-JDBC，但没分库分表，只是做了设计上的预留。正好老师也是这块的专家，也讲了这块，更加坚定我深入研究ShardingSphere并应用到实际。

## 6.1.     垂直拆分

## 6.2.     水平拆分

### 6.2.1.     分库

### 6.2.2.     分表

### 6.2.3.     分库分表

## 6.3.     数据的分类管理

## 6.4.     框架和中间件

### 6.4.1.     ShardingSphere-JDBC

### 6.4.2.     ShardingSphere-Proxy

## 6.5.     数据迁移

### 6.5.1.     全量

### 6.5.2.     全量+增量

### 6.5.3.     binlog+全量+增量

### 6.5.4.     ShardingSphere-scaling

## 6.6.     分布式事务

### 6.6.1.     XA

### 6.6.2.     BASE柔性事务

#### 6.6.2.1.      TCC

#### 6.6.2.2.      SAGA

#### 6.6.2.3.      AT

#### 6.6.2.4.      隔离级别

#### 6.6.2.5.      Seata

#### 6.6.2.6.      hmily

# 7.    RPC和微服务
之前用Erlang语言开发时，研究过Hessian、Thrift、Protocol Buffer、gRPC；听课后又加深了印象，提高了认识。但由于是小公司，微服务基本不用；之前有两个转手过来的项目，一个用了Dubbo，另一个用了Spring Cloud 1.0，但都理解不深；借本课程契机，可以深入学习下。

## 7.1.     RPC

### 7.1.1.     原理

### 7.1.2.     代理

### 7.1.3.     序列化

### 7.1.4.     网络传输

### 7.1.5.     查找实现类

### 7.1.6.     框架

#### 7.1.6.1.      Hessian

#### 7.1.6.2.      Thrift

#### 7.1.6.3.      Protocol Buffer

#### 7.1.6.4.      gRPC

## 7.2.     Dubbo

### 7.2.1.     整体架构

### 7.2.2.     框架设计

### 7.2.3.     SPI

### 7.2.4.     服务暴露

### 7.2.5.     服务引用

### 7.2.6.     集群和路由

### 7.2.7.     泛化引用

### 7.2.8.     隐式传参

### 7.2.9.     mock

### 7.2.10.   应用场景

#### 7.2.10.1.    分布式服务化改造

#### 7.2.10.2.    开放平台

#### 7.2.10.3.    BFF

#### 7.2.10.4.    服务化中台

### 7.2.11.   最佳实践

#### 7.2.11.1.    开发分包

#### 7.2.11.2.    环境隔离和分组

#### 7.2.11.3.    参数配置

#### 7.2.11.4.    容器化部署

#### 7.2.11.5.    运维与监控

#### 7.2.11.6.    分布式事务

##### 7.2.11.6.1.     不支持XA

#### 7.2.11.7.    重试和幂等

## 7.3.     分布式服务化

### 7.3.1.     服务治理

### 7.3.2.     配置/注册/元数据中心

### 7.3.3.     服务的注册和发现

### 7.3.4.     服务的集群和路由

### 7.3.5.     服务过滤和流控

## 7.4.     微服务架构

### 7.4.1.     发展史

### 7.4.2.     应用场景

### 7.4.3.     最佳实践

## 7.5.     Spring Cloud

### 7.5.1.     Config/Eureka/Consul

### 7.5.2.     Zuul/Zuul2/Spring Cloud Gateway

### 7.5.3.     Feign/Ribbon

### 7.5.4.     Hytrix/Alibaba Sentinel

## 7.6.     微服务相关框架与工具

### 7.6.1.     APM

### 7.6.2.     权限控制

### 7.6.3.     数据处理

### 7.6.4.     网关与通信

# 8.    分布式缓存
Redis常用，之前还用过Couchbase。缓存是个好东西，有了课程的开眼界，在未来是实战中，我会尝试选型更多的技术或技术使用方式。

## 8.1.     本地缓存

## 8.2.     远程缓存

## 8.3.     缓存策略

### 8.3.1.     容量

### 8.3.2.     过期

## 8.4.     常见问题

### 8.4.1.     缓存穿透

### 8.4.2.     缓存击穿

### 8.4.3.     缓存雪崩

## 8.5.     Redis

### 8.5.1.     性能测试

### 8.5.2.     5种基本数据结构

### 8.5.3.     3种高级数据结构

### 8.5.4.     使用场景

### 8.5.5.     Java客户端

### 8.5.6.     与Spring整合

### 8.5.7.     高级

#### 8.5.7.1.      事务

#### 8.5.7.2.      Lua

#### 8.5.7.3.      管道技术

#### 8.5.7.4.      数据备份与恢复

##### 8.5.7.4.1.       RDB

##### 8.5.7.4.2.       AOF

#### 8.5.7.5.      性能优化

##### 8.5.7.5.1.       内存优化

##### 8.5.7.5.2.       CPU优化

#### 8.5.7.6.      分区

#### 8.5.7.7.      经验

### 8.5.8.     集群和高可用

## 8.6.     Redission

## 8.7.     Hazelcast

# 9.    分布式消息队列
消息队列之前有所关注，但一直也没应用过。有了本次课的讲解，让我在最新的项目中，硬性引入了RabbitMQ，目前实战良好。目前正在调研Camel。打算可能是数据迁移--Mongo到MySQL。

## 9.1.     消息模式与消息协议

## 9.2.     ActiveMQ

## 9.3.     Kafka

## 9.4.     RabbitMQ

## 9.5.     RocketMQ

## 9.6.     Pulsar

## 9.7.     EIP/Camel/Spring Integration
