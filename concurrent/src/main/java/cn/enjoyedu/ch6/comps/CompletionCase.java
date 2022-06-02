package cn.enjoyedu.ch6.comps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *类说明：
 */
public class CompletionCase {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors()*10;

    // 方法一，自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception {
    	long start = System.currentTimeMillis();
    	AtomicInteger count = new AtomicInteger(0);
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        //队列,拿任务的执行结果
        BlockingQueue<Future<Integer>> queue = 
        		new LinkedBlockingQueue<>();
        System.out.println("当前系统的cpu数量：" + TOTAL_TASK);

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<Integer> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);
        }


        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
        	int sleptTime = queue.take().get();
        	//System.out.println(" slept "+sleptTime+" ms ...");        	
        	count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        System.out.println("-------------tasks sleep time "+count.get()
        		+"ms,and spend time "
        		+(System.currentTimeMillis()-start)+" ms");
    }

    public void testByCompletion() throws Exception{
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        //使用CompletionService替换阻塞队列
        CompletionService<Integer> cSevice
                = new ExecutorCompletionService<>(pool);

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            cSevice.submit(new WorkTask("ExecTask" + i));
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            int sleptTime = cSevice.take().get();
            //System.out.println(" slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        System.out.println("-------------tasks sleep time "+count.get()
                +"ms,and spend time "
                +(System.currentTimeMillis()-start)+" ms");

    }

    public static void main(String[] args) throws Exception {
        CompletionCase t = new CompletionCase();
        t.testByQueue();
//        t.testByCompletion();
        /**
         * 在不使用CompletionService的时候，上一个任务没有执行完，下一个任务的结果是拿不到的；
         * 假如第一个任务的执行时间很长，那么下一个任务的结果是拿不到的；
         * 但是在CompletionService里面改写了FutureTask执行的方法，也就是在提交任务（submit）的时候，
         * 会对任务进行包装，那么在提交结果的时候，谁先执行完谁就会先放入CompletionService的结果队列中，
         * 它也是线程池和阻塞队列的结合体，优点是优先获取到先结束任务的执行结果；
         */
    }
}
