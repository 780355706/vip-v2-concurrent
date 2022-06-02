package cn.enjoyedu.ch1.myTest;

/**
 * @Description 验证每开启一个线程线程开启一个1M的栈空间；
 */
public class MultiThreadTest {
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " already begin...");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(200);
            new Thread(new MyRunnable()).start();
        }
    }
}
