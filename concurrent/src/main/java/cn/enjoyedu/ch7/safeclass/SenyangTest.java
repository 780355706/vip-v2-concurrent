package cn.enjoyedu.ch7.safeclass;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SenyangTest {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
        4,3, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(10),
        new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 3; i++) {
//            threadPool.execute(new MyTask());
            threadPool.execute(new MyTask2());
        }
        threadPool.shutdown();
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            ImmutableClassToo ict = new ImmutableClassToo();
            boolean contain = ict.isContain(1);
            System.out.println(contain);
        }
    }

    static class MyTask2 implements Runnable {

        @Override
        public void run() {
            ImmutableClass ic = new ImmutableClass(3);
            UserVo user = ic.getUser();
            user.setAge(new Random().nextInt(100));
            System.out.println(user.getAge());
        }
    }
}
