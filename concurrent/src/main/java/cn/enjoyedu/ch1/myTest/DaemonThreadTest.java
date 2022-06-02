package cn.enjoyedu.ch1.myTest;

/**
 * @Description 验证守护线程随着非守护线程的终止而终止
 * @ProjectName vip-v2-concurrent
 * @Package cn.enjoyedu.ch1.myTest
 * @Classname DaemonThreadTest
 * @Author DengSenyang
 * @CreateDate 2021/11/29 19:18
 */
public class DaemonThreadTest {
    static class DaemonRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("daemonThread is running...");
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("daemon Thread is completed");
        }
    }

    static class NoDaemonRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("noDaemonThread is completed");
        }
    }

    public static void main(String[] args) {
        Thread daemonThread = new Thread(new DaemonRunnable());
        daemonThread.setDaemon(true);
        daemonThread.start();
        new Thread(new NoDaemonRunnable()).start();
    }
}
