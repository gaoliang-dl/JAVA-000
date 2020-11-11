package gaol.practice.class4;

import java.util.concurrent.CountDownLatch;

public class MyThreadReturnCountDownLatch implements Runnable{
    /** 模拟线程执行完毕后主程序要获取的值*/
    private int returnValue = 0;
    final static CountDownLatch countDownLatch = new CountDownLatch(1);
    @Override
    public void run() {
        System.out.println("线程执行......");
        returnValue = Class4Application.sum();
        System.out.println("线程执行完毕......");
        countDownLatch.countDown();
    }
    public int getReturnValue() {
        return returnValue;
    }
}
