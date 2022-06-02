package cn.enjoyedu.ch2.tools.myTest;

import cn.enjoyedu.ch2.forkjoin.sum.MakeArray;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class TempTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {




        MyCallable myCallable = new MyCallable(MakeArray.makeArray());
        FutureTask<Long> myFutureTask = new FutureTask<Long>(myCallable);
        new Thread(myFutureTask).start();
        Long result = myFutureTask.get();
        System.out.println(result);
        System.out.println(400000000 * 1.5 * 400000000);
    }

    private static class MyCallable implements Callable<Long> {

        private int[] arr;

        private MyCallable (int[] arr) {
            this.arr = arr;
        }

        @Override
        public Long call() throws Exception {
            long result = 0;
            for (int i = 0; i < arr.length; i++) {
                result += arr[i];
            }
            return result;
        }
    }
}
