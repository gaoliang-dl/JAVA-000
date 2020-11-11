package gaol.practice.class4;

import java.util.concurrent.Semaphore;

public class MyThreadReturnSemaphore implements Runnable{
    /** 模拟线程执行完毕后主程序要获取的值*/
    private int returnValue = 0;
    final static Semaphore semaphore = new Semaphore(1);
    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("线程执行......");
            returnValue = Class4Application.sum();
            System.out.println("线程执行完毕......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
    public int getReturnValue() {
        return returnValue;
    }
}
