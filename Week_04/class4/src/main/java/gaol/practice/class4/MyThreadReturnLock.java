package gaol.practice.class4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadReturnLock implements Runnable{
    /** 模拟线程执行完毕后主程序要获取的值*/
    private int returnValue = 0;
    final static Lock lock = new ReentrantLock();
    @Override
    public void run() {
        lock.lock();
        System.out.println("线程执行......");
        returnValue = Class4Application.sum();
        System.out.println("线程执行完毕......");
        lock.unlock();
    }
    public int getReturnValue() {
        return returnValue;
    }
}
