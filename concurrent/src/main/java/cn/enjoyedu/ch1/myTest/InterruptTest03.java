package cn.enjoyedu.ch1.myTest;

public class InterruptTest03 {
    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            while(true) {
                System.out.println(Thread.currentThread().getName() + " is running ");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new MyRunnable());
        myThread.start();
//        Thread.currentThread().sleep(1000);
        myThread.interrupt();
    }
}
