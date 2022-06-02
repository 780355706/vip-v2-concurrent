package cn.enjoyedu.ch1.myTest;

public class InterruptTest02 {

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + "线程并未中断");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread myThread = new Thread(new MyRunnable());
        myThread.start();
        myThread.interrupt();
    }
}
