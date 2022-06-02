package cn.enjoyedu.ch1.myTest;

/**
 * @Description 校验interrupt()方法是否释放锁
 * @ProjectName vip-v2-concurrent
 * @Package cn.enjoyedu.ch1.myTest
 * @Classname InterruptTest01
 * @Author DengSenyang
 * @CreateDate 2021/11/29 18:22
 */
public class InterruptTest01 {
    static Object syn = new Object();
    static class MyRunnable01 implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
                synchronized (syn) {
                    System.out.println(Thread.currentThread().getName() + " is already get lock...");
                    System.out.println(Thread.currentThread().getState());
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName() + " is released...");//打印出改行语句说明当前线程并没有理会interrupt()
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyRunnable02 implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                synchronized (syn) {
                    System.out.println(Thread.currentThread().getName() + " is already get lock...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread01 = new Thread(new MyRunnable01());
        thread01.start();
        new Thread(new MyRunnable02()).start();
    }
}
