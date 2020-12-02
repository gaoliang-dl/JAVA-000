## 第14节 必做 读写分离-动态切换数据源版本1.0
#### curl http://localhost:8080/user/master
[{"id":1,"name":"a"}]
#### curl http://localhost:8080/user/slave
[{"id":1,"name":"b"}]
#### 代码
[week7multidatasrc](week7multidatasrc)
