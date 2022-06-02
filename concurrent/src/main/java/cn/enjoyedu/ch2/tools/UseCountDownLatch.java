package cn.enjoyedu.ch2.tools;

import cn.enjoyedu.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 *类说明：演示CountDownLatch用法，
 * 共6个初始化子线程，6个闭锁扣除点，扣除完毕后，主线程和业务线程才能继续执行
 */
public class UseCountDownLatch {

    //只有当闭锁扣除点扣除为0后，主线程和业务线程BusiThread才能被唤醒继续执行；
    //注意：计数器和扣减的个数需要完全对应上
    static CountDownLatch latch = new CountDownLatch(6);


    /*初始化线程*/
    private static class InitThread implements Runnable{

        public void run() {
            System.out.println("Thread_"+Thread.currentThread().getId()
        			+" ready init work......");
            latch.countDown();
            for(int i =0;i<2;i++) {
            	System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ........continue do its work");
            }
        }
    }

    /*业务线程等待latch的计数器为0完成*/
    private static class BusiThread implements Runnable{

        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i =0;i<3;i++) {
            	System.out.println("BusiThread_"+Thread.currentThread().getId()
            			+" do business-----");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //这个线程扣减2次
        new Thread(new Runnable() {
            public void run() {
            	SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 1st......");
                latch.countDown();
                System.out.println("begin step 2nd.......");
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 2nd......");
                latch.countDown();
            }
        }).start();
        new Thread(new BusiThread()).start();
        //扣减4次
        for(int i=0;i<4;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        //主线程也等待所有的工作做完之后才继续往下走
        latch.await();
        System.out.println("Main do ites work........");
    }


    public void testManyThread() throws InterruptedException {
        //这个线程扣减2次
        new Thread(new Runnable() {
            public void run() {
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 1st......");
                latch.countDown();
                System.out.println("begin step 2nd.......");
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 2nd......");
                latch.countDown();
            }
        }).start();
        new Thread(new BusiThread()).start();
        //扣减4次
        for(int i=0;i<4;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        //主线程也等待所有的工作做完之后才继续往下走
        latch.await();
        System.out.println("Main do ites work........");
    }

}
