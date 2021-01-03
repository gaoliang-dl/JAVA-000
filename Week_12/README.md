## （必做）配置 redis 的主从复制，sentinel 高可用，Cluster 集群。
### 配置 redis 的主从复制
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
[redis6380](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/redis6380.conf)  
### sentinel 高可用
- 启动命令
```bash
./redis-sentinel sentinel.conf
./redis-sentinel sentinel26380.conf
```
- 关闭主redis  
- redis从库变为了主库
- 截图  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/截屏2021-01-03%20上午12.44.34.png)   
- 配置文件   
[sentinel26380](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/sentinel26380.conf)  
### Cluster 集群
- 关键配置
```bash
cluster-enabled yes
port 6380
port 6381
pidfile "/var/run/redis_6380.pid"
pidfile "/var/run/redis_6381.pid"
dir "/Users/a/Downloads/redis-6.0.9/2"
dir "/Users/a/Downloads/redis-6.0.9/3"
```
- 启动命令
```bash
../src/redis-server redis6379.conf
../src/redis-server redis6380.conf
../src/redis-server redis6381.conf
src/redis-cli --cluster create 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381
src/redis-cli -c
```
- 关键输出
```
➜  redis-6.0.9 src/redis-cli --cluster create 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381
>>> Performing hash slots allocation on 3 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
M: fd7e55d243ad5c3218e72bc0b370bef11c676286 127.0.0.1:6379
   slots:[0-5460] (5461 slots) master
M: f8bc2939bdd1afde0beaa40690f0fbfe8617d8b8 127.0.0.1:6380
   slots:[5461-10922] (5462 slots) master
M: 5e87eab365e2212c2f7228b8f8af689f62948f56 127.0.0.1:6381
   slots:[10923-16383] (5461 slots) master
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
...
>>> Performing Cluster Check (using node 127.0.0.1:6379)
M: fd7e55d243ad5c3218e72bc0b370bef11c676286 127.0.0.1:6379
   slots:[0-5460] (5461 slots) master
M: f8bc2939bdd1afde0beaa40690f0fbfe8617d8b8 127.0.0.1:6380
   slots:[5461-10922] (5462 slots) master
M: 5e87eab365e2212c2f7228b8f8af689f62948f56 127.0.0.1:6381
   slots:[10923-16383] (5461 slots) master
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```
```bash
➜  redis-6.0.9 src/redis-cli -c    
127.0.0.1:6379> get a
-> Redirected to slot [15495] located at 127.0.0.1:6381
"1"
127.0.0.1:6381> set b 1
-> Redirected to slot [3300] located at 127.0.0.1:6379
OK
127.0.0.1:6379> set c 1
-> Redirected to slot [7365] located at 127.0.0.1:6380
OK
127.0.0.1:6380> 
```
- 截图  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/cluster/截屏2021-01-03%20下午2.49.22.png)  
![image](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/cluster/截屏2021-01-03%20下午2.49.51.png)  
- 配置文件  
[6379](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/cluster/redis6379.conf)  
[6380](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/cluster/redis6380.conf)   
[6381](https://github.com/gaoliang-dl/JAVA-000/blob/main/Week_12/cluster/redis6381.conf) 
