## （必做）基于 Redis 封装分布式数据操作：在 Java 中实现一个简单的分布式锁
```java
	public static void distributeLock() {
		// 模拟分布式，新建3个线程
		ExecutorService controllerThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			controllerThreadPool.execute(() -> {
				// 连接redis
				Jedis jedis = new Jedis("localhost", 6379);
				String threadName = Thread.currentThread().getName();
				//设置锁
				String lockResult = jedis.set("lock", "1", "nx", "ex", 30L);
				if (lockResult == null) {
					System.out.println(threadName+"获得锁失败");
				} else if (lockResult.equals("OK")) {
					System.out.println(threadName+"获得了锁");
				} else {
					System.out.println(threadName+"获得锁时出现了异常");
				}
			});
		}
		controllerThreadPool.shutdown();
	}
```
#### 输出
```bash
pool-1-thread-3获得了锁
pool-1-thread-2获得锁失败
pool-1-thread-1获得锁失败
```
## （必做）基于 Redis 封装分布式数据操作：在 Java 中实现一个分布式计数器，模拟减库存
```java
	public static void countDown() {
		// 初始7个库存
		Jedis jedis2 = new Jedis("localhost", 6379);
		jedis2.set("count-down", "7");
		// 模拟分布式，新建10个线程
		ExecutorService controllerThreadPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			controllerThreadPool.execute(() -> {
				// 连接redis
				Jedis jedis = new Jedis("localhost", 6379);
				String threadName = Thread.currentThread().getName();
				//预先减库存
				Long decr = jedis.decr("count-down");
				if (decr < 0) { // 扣库存失败
					System.out.println(threadName+"扣库存失败！！！"+decr);
				} else {
					System.out.println(threadName+"扣库存成功"+decr);
				}
			});
		}
		controllerThreadPool.shutdown();
	}
```
#### 输出
```bash
pool-2-thread-1扣库存成功6
pool-2-thread-5扣库存成功2
pool-2-thread-6扣库存成功1
pool-2-thread-2扣库存成功4
pool-2-thread-3扣库存成功5
pool-2-thread-4扣库存成功3
pool-2-thread-8扣库存失败！！！-1
pool-2-thread-7扣库存成功0
pool-2-thread-9扣库存失败！！！-2
pool-2-thread-10扣库存失败！！！-3
```
## （必做）基于 Redis 的 PubSub 实现订单异步处理
```java
	public static void pubSub() throws InterruptedException {
		final String ch = "myChannel";
		//订阅者
		Thread sub = new Thread(() -> {
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.subscribe(new Subscriber(), ch);
		});
		//发布者
		Thread pub = new Thread(() -> {
			Jedis jedis = new Jedis("localhost", 6379);
			for (int i = 0; i < 10; i++) {
				jedis.publish(ch, "message" + i);
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		sub.start();
		pub.start();
		pub.join();
	}
```
#### 输出
```bash
subscribe redis channel success, channel myChannel, subscribedChannels 1
receive redis published message, channel myChannel, message message1
receive redis published message, channel myChannel, message message2
receive redis published message, channel myChannel, message message3
receive redis published message, channel myChannel, message message4
receive redis published message, channel myChannel, message message5
receive redis published message, channel myChannel, message message6
receive redis published message, channel myChannel, message message7
receive redis published message, channel myChannel, message message8
receive redis published message, channel myChannel, message message9
```
