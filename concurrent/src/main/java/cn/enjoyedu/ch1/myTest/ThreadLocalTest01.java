package cn.enjoyedu.ch1.myTest;

/**
 * @Description 演示普通静态成员变量和ThreadLocal静态成员变量的线程安全性
 */
public class ThreadLocalTest01 {
    static class MyRunnable1 implements Runnable {
        private static ThreadLocal<Integer> count = new ThreadLocal<Integer>();

        @Override
        public void run() {
            Integer value = count.get();
            if (null == value) {
                count.set(1);
                value = 1;
            }
            System.out.println(Thread.currentThread().getName() + "--" + value);
            count.set(value + 1);
        }
    }

    static class MyRunnable2 implements Runnable {
        private static Integer count = 1;

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "--" + count);
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread thread01 = new Thread(new MyRunnable1());
            thread01.start();
        }
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < 100; i++) {
            Thread thread02 = new Thread(new MyRunnable2());
            thread02.start();
        }
    }
}
