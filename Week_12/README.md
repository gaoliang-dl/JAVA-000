## （必做）配置 redis 的主从复制，sentinel 高可用，Cluster 集群。
#### 配置 redis 的主从复制
- 从库关键配置
```bash
port 6380
slaveof 127.0.0.1 6379
```
- 启动命令
```bash
./redis-server
./redis-server redis6380.conf
./redis-cli
info
set a 1
./redis-cli -h localhost -p 6380
info
get a
set b 1
```
- 执行结果截图  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/截屏2021-01-02%20下午11.43.47.png)   
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/截屏2021-01-02%20下午11.44.07.png)   
- 从库配置文件
[配置文件](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/redis6380.conf)  
#### sentinel 高可用
- 启动命令
```
./redis-sentinel sentinel.conf
./redis-sentinel sentinel26380.conf
```
- 关闭主redis  
- redis从库变为了主库
- 截图  

- 配置文件 
