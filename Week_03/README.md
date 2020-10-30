# 实现一个网关
## 周四作业：整合你上次作业的 httpclient/okhttp  
### 网关的后端服务，使用老师之前提供的gateway-server-0.0.1-SNAPSHOT.jar

- java -jar .\gateway-server-0.0.1-SNAPSHOT.jar
- curl http://127.0.0.1:8088/api/hello

### 网关基础代码使用老师提供的nio02

- https://github.com/kimmking/JavaCourseCodes/02nio/nio02
- http://127.0.0.1:8888/

### 开启网关的inbound测试内容

- curl http://127.0.0.1:8888/test
- 网关不调用后端server http://127.0.0.1:8088/api/hello，而是直接返回“hello,kimmking”

### 将上周httpclient作业，整合到本期，用于处理outbound部分

- https://github.com/gaoliang-dl/JAVA-000/tree/main/Week_02/httpclient
- 在本工程被合并到io.github.kimmking.gateway.outbound.httpclient.GaoLiangHttpOutboundHandler

### 最终forward成功

- curl http://127.0.0.1:8888/api/hello
- 返回“hello word”
