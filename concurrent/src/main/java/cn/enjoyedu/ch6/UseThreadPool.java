package cn.enjoyedu.ch6;

import cn.enjoyedu.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.*;

/**
 *类说明：线程池的使用范例
 */
public class UseThreadPool {
    /*没有返回值*/
    static class Worker implements Runnable
    {
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName){
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName()
            		+" process the task : " + taskName);
            SleepTools.ms(r.nextInt(100)*5);
        }
    }

    /*有返回值*/
    static class CallWorker implements Callable<String>{
    	
        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName){
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }    	

		@Override
		public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()
            		+" process the task : " + taskName);
            return Thread.currentThread().getName()+":"+r.nextInt(100)*5;
		}
    	
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        int coreNum = Runtime.getRuntime().availableProcessors();//逻辑核心
        System.out.println("计算机的逻辑核心数：" + coreNum);
//        ExecutorService threadPool = new ThreadPoolExecutor(2,
//                4,3,TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(10),
//                new ThreadPoolExecutor.DiscardOldestPolicy());

        //下面是jdk中预定义的线程池，但是在阿里的编程规范中，不建议使用jdk预定义的线程池，一般使用上面的方式创建线程池
        /**
         * 看构造方法可以知道：
         * 创建固定线程数的线程池，主要应用于限制线程数量的场景；（核心线程数和最大线程数相同，并且失效时
         * 间是0，多余的空闲线程会立刻被终止，任务队列使用的是LinkedBlockingQueue，长度是Integer.MAX_VALUE，这也是阿里不建议
         * 使用它的原因之一，可能会把内存称爆，需要再固定的场景下使用）
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        /**
         * 看构造方法可以知道：
         * 线程池中只有一个线程，核心线程数和最大线程数的数量都是1，保证任务的串行执行，
         * 任务可以按照顺序依次执行。同样使用LinkedBlockingQueue作为任务队列，容量可以达到Integer.MAX_VALUE
         */
        ExecutorService threadPool1 = Executors.newSingleThreadExecutor();
        /**
         * 看构造方法可以知道：
         * 每进来一个任务，就会在线程池中创建一个新的线程，构造函数中使用的任务队列是SynchronousQueue，该队列不存储任何
         * 任务，每进来一个任务，必须有有一个线程来执行它。如果任务十分短小，很快就执行完了，那么可以考虑使用，或者在某些固定的场景
         */
        ExecutorService threadPool2 = Executors.newCachedThreadPool();
        /**
         * 看构造方法可以知道：
         * 使用ForkJoinPool实现的线程池，它额外的特点就是工作密取
         * 默认采用计算机物理核心数作为线程池的核心线程数量，也可以自己指定
         */
        ExecutorService threadPool3 = Executors.newWorkStealingPool();
        /**
         * 多线程定时任务：
         * cn.enjoyedu.ch6.schd.ScheduledCase
         */
        ExecutorService threadPool4 = Executors.newScheduledThreadPool(2);
        /**
         * 单线程定时任务：
         */
        ExecutorService threadPool5 = Executors.newSingleThreadScheduledExecutor();


        for (int i = 0; i <= 6; i++)
        {
            Worker worker = new Worker("worker " + i);
            System.out.println("A new task has been added : " + worker.getName());
            threadPool.execute(worker);//提交无返回值的任务
        }
        
        for (int i = 0; i <= 6; i++)
        {
        	CallWorker callWorker = new CallWorker("worker " + i);
            System.out.println("A new task has been added : " + callWorker.getName());
            Future<String> result = threadPool.submit(callWorker);//提交有返回值的任务
            System.out.println(result.get());
        }
        threadPool.shutdown();
        threadPool.shutdownNow();
    }
}
