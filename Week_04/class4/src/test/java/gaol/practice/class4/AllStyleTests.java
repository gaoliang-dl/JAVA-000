package gaol.practice.class4;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

//@SpringBootTest
class AllStyleTests {

    @Test
    void style_1_runnable_join() throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        MyThreadReturn myThreadReturn = new MyThreadReturn();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        // 使用join
        thread.join();
        //这是得到的返回值
        int result = myThreadReturn.getReturnValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    @Test
    void style_2_runnable_sleep() throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        MyThreadReturn myThreadReturn = new MyThreadReturn();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        // 使用Sleep
        Thread.sleep(3000);
        //这是得到的返回值
        int result = myThreadReturn.getReturnValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    @Test
    void style_3_callable() throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        Thread thread = new Thread(futureTask);
        thread.start();
        //这是得到的返回值
        int result = futureTask.get();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    @Test
    void style_4_future() throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> submit = executorService.submit(new MyCallable());
        //这是得到的返回值
        int result = submit.get();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        //结束线程
        executorService.shutdown();
    }

    @Test
    void style_5_loop() throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        MyThreadReturn myThreadReturn = new MyThreadReturn();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        // 通过while循环判断
        while (myThreadReturn.getReturnValue() == 0) {
            Thread.sleep(100);
        }
        int result = myThreadReturn.getReturnValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    @Test
    void style_6_lock() throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        MyThreadReturnLock myThreadReturn = new MyThreadReturnLock();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        // Lock
        Thread.sleep(20);
        MyThreadReturnLock.lock.lock();
        //这是得到的返回值
        int result = myThreadReturn.getReturnValue();
        MyThreadReturnLock.lock.unlock();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    @Test
    void style_7_semaphore() throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        MyThreadReturnSemaphore myThreadReturn = new MyThreadReturnSemaphore();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        // 获取资源
        Thread.sleep(20);
        MyThreadReturnSemaphore.semaphore.acquire();
        //这是得到的返回值
        int result = myThreadReturn.getReturnValue();
        MyThreadReturnSemaphore.semaphore.release();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    @Test
    void style_8_synchronized() throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        MyThreadReturnSynchronized myThreadReturn = new MyThreadReturnSynchronized();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        Thread.sleep(20);
        //这是得到的返回值
        int result = myThreadReturn.getReturnValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

}
