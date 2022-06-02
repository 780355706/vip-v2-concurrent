package cn.enjoyedu.ch1.myTest;

public class WaitNotify001 {

    private static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        MyRunnable runnable = new MyRunnable();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
//        Thread t = new Thread(runnable);
//        t.start();
        Thread.sleep(3000);
        synchronized (obj) {
            obj.notify();
            Thread.sleep(300);
            obj.notify();
        }

    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "正在等待锁");
                synchronized (obj) {
                    System.out.println(Thread.currentThread().getName() + "获得锁");
                    System.out.println(Thread.currentThread().getName() + "进入等待状态");
                    obj.wait();
                    System.out.println(Thread.currentThread().getName() + "已经被唤醒");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
