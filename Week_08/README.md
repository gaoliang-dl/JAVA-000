## Week08 周四 必做：设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github
```properties
# 分库
spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=id
spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=ds$->{id % 2}
# 分表
spring.shardingsphere.sharding.tables.user.actual-data-nodes=ds$->{0..1}.user_$->{0..15}
spring.shardingsphere.sharding.tables.user.table-strategy.inline.sharding-column=id
spring.shardingsphere.sharding.tables.user.table-strategy.inline.algorithm-expression=user_$->{id % 16}
spring.shardingsphere.sharding.tables.user.key-generator.column=id
spring.shardingsphere.sharding.tables.user.key-generator.type=SNOWFLAKE
```
## Week08 周六 必做：基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github
```java
    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public List<User> addTenUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder().username("a"+i).mobile("1861234123"+i).build();
            userService.save(user);
            list.add(user);
        }
        return list;
    }
```
