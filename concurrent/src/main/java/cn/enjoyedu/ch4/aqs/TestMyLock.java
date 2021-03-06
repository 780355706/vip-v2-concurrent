package cn.enjoyedu.ch4.aqs;

import cn.enjoyedu.tools.SleepTools;

import java.util.concurrent.locks.Lock;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class TestMyLock {

    public void test() {
        final Lock lock = new SelfLock();//TrinityLock没有测试类，测试的时候修改这个
        class Worker extends Thread {

			public void run() {
                lock.lock();
                System.out.println(Thread.currentThread().getName());
                try {
                    SleepTools.second(1);
                } finally {
                    lock.unlock();
                }
            }
        }
        // 启动4个子线程
        for (int i = 0; i < 4; i++) {
            Worker w = new Worker();
            //w.setDaemon(true);
            w.start();
        }
        // 主线程每隔1秒换行
        for (int i = 0; i < 10; i++) {
        	SleepTools.second(1);
            //System.out.println();
        }
    }

    public static void main(String[] args) {
        TestMyLock testMyLock = new TestMyLock();
        testMyLock.test();
    }
}
