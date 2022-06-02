package cn.enjoyedu.ch2.tools.myTest;

import java.util.concurrent.CountDownLatch;


/**
 * @Description 在单实例多线程中如何使用CountDownLatch
 * @ProjectName vip-v2-concurrent
 * @Package cn.enjoyedu.ch2.tools.myTest
 * @Classname CountLatchTest
 * @Author DengSenyang
 * @CreateDate 2021/11/30 11:58
 * 在多线程单实例对象中不能使用CountDownLatch，需要借助ThreadLocal，抽出一个处理多线程业务的类UseCountDownLatchTest，
 * 然后创建该类的实例，并传入各自的CountDownLatch以实现多线程操作自己的CountDownLatch
 */
public class CountLatchTest {
    static ThreadLocal<CountDownLatch> threadLocal = new ThreadLocal<CountDownLatch>(){
        @Override
        protected CountDownLatch initialValue() {
            return new CountDownLatch(6);
        }
    };
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(threadLocal);
                        System.out.println(threadLocal.get());
                        UseCountDownLatchTest user = new UseCountDownLatchTest(threadLocal.get());
                        user.testManyThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
