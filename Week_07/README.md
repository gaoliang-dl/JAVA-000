## 第14节 必做 读写分离-动态切换数据源版本1.0
#### curl http://localhost:8080/user/master
[{"id":1,"name":"a"}]
#### curl http://localhost:8080/user/slave
[{"id":1,"name":"b"}]
#### 代码
[week7multidatasrc](week7multidatasrc)
  
## 第14节 必做 读写分离-数据库框架版本2.0
#### curl http://localhost:8080/user/master
[{"id":1,"name":"a"}]
#### curl http://localhost:8080/user/slave
[{"id":1,"name":"b"}]
#### 代码
[week7shardingsphere](week7shardingsphere)
  
## 第13节 必做 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
#### 快效率：使用PreparedStatement.addBatch和PreparedStatement.executeBatch
```sql
INSERT INTO `test`.`t_order` (`order_id`, `user_id`, `cost_count`, `status`, `snapshot`) VALUES
```
```java
suffix.append("(" + SnowflakeIdUtil.getIdUtilNo() + "," + SnowflakeIdUtil.getIdUtilNo() + "," + 1000 + "," + 1 + ",'snapshot'),");
String sql = prefix + suffix.substring(0, suffix.length() - 1);
preparedStatement.addBatch(sql);
preparedStatement.executeBatch();
```
#### 慢效率：使用PreparedStatement.executeUpdate
```sql
INSERT INTO `test`.`t_order` (`order_id`, `user_id`, `cost_count`, `status`, `snapshot`) VALUES (?, ?, ?, ?, ?);
```
```java
preparedStatement.setLong(1, SnowflakeIdUtil.getIdUtilNo());
preparedStatement.setLong(2, SnowflakeIdUtil.getIdUtilNo());
preparedStatement.setLong(3, 1000L);
preparedStatement.setInt(4, 1);
preparedStatement.setString(5, "snapshot");
preparedStatement.executeUpdate();
```
