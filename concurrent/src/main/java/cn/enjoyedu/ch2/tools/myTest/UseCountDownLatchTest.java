package cn.enjoyedu.ch2.tools.myTest;

import cn.enjoyedu.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 *类说明：演示CountDownLatch用法，
 * 共6个初始化子线程，6个闭锁扣除点，扣除完毕后，主线程和业务线程才能继续执行
 */
public class UseCountDownLatchTest {

     private CountDownLatch latch;

     public UseCountDownLatchTest (CountDownLatch latch) {
         this.latch = latch;
     }


    /*初始化线程*/
    private  class InitThread implements Runnable{

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
    private  class BusiThread implements Runnable{

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
