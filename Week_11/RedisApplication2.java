package io.kimmking.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisApplication2 {

	/**
     * 基于 Redis 封装分布式数据操作：在 Java 中实现一个简单的分布式锁
	 */
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

    /**
     * 基于 Redis 封装分布式数据操作：在 Java 中实现一个分布式计数器，模拟减库存
	 */
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

    /**
	 * 基于 Redis 的 PubSub 实现订单异步处理
	 */
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

	public static void main(String[] args) throws InterruptedException {
		System.out.println("====================== 1 START");
		distributeLock();
		Thread.sleep(2000L);
		System.out.println("====================== 1 END");
		System.out.println("====================== 2 START");
		countDown();
		Thread.sleep(2000L);
		System.out.println("====================== 2 END");
		System.out.println("====================== 3 START");
		pubSub();
		System.out.println("====================== 3 END");
	}

	public static class Subscriber extends JedisPubSub {
		public Subscriber() {
		}

		public void onMessage(String channel, String message) {
			System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
		}

		public void onSubscribe(String channel, int subscribedChannels) {
			System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
					channel, subscribedChannels));
		}

		public void onUnsubscribe(String channel, int subscribedChannels) {
			System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
					channel, subscribedChannels));
		}
	}
}
