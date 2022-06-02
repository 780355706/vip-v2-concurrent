package cn.enjoyedu.ch6.schd;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *类说明：演示ScheduledThreadPoolExecutor的用法
 */
public class ScheduledCase {
    public static void main(String[] args) {
    	
    	ScheduledThreadPoolExecutor schedule
                = new ScheduledThreadPoolExecutor(1);
    	//定时任务的四种提交方式：
        /**
         * 1.
         * 延时Runnable任务（仅执行一次）
         * 既可以执行Runnable，也可以执行Callable
         */
//        schedule.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("the task only run once!");
//            }
//        },3000,TimeUnit.MILLISECONDS);

        /**
         * 2.
         * 上一个任务的结束时间与下一个任务的开始时间是一个固定值；
         * 固定延时间隔执行的任务
         */
//        schedule.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("*******fixDelay start,"
//                        +ScheduleWorker.formater.format(new Date()));
//                SleepTools.second(2);
//                System.out.println("*******fixDelay end,"
//                        +ScheduleWorker.formater.format(new Date()));
//            }
//        },1000,3000,TimeUnit.MILLISECONDS);


        /**
         * 3.
         * 任务之间的开始时间间隔是一个固定值；
         * 举例：
         * 固定时间间隔执行的任务,从理论上说第二次任务在6000 ms后执行，第三次在6000*2 ms后执行
         * 如果任务的执行时间超过了定义的间隔时间，那么下一个任务将延迟到该任务执行结束在执行，
         * 如果下一个任务没有超过其正常的延迟时间，那么将等到正常的延迟时间再执行，具体案例请执行
         * 下面代码
         */
//        schedule.scheduleAtFixedRate(
//                new ScheduleWorkerTime(),0,6000,
//                TimeUnit.MILLISECONDS);

        /**
         * 如果任务在执行的过程中抛出了异常会怎样？
         * 1. 固定时间间隔执行的任务,开始执行后就触发异常,next周期将不会运行
         * 2. 固定时间间隔执行的任务,虽然抛出了异常,但被捕捉了,next周期继续运行
         * 如果不捕捉异常，那么schedule将会把异常吞掉，不会打印出异常信息
         * 所以在schedule的时候需要使用try{}将run方法中所有的代码都包裹起来，
         * 否则不会打印异常信息
         */
        //        // 固定时间间隔执行的任务,开始执行后就触发异常,next周期将不会运行
        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException),
                0, 3000, TimeUnit.MILLISECONDS);

//        // 固定时间间隔执行的任务,虽然抛出了异常,但被捕捉了,next周期继续运行
//        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.ProcessException),
//                0, 3000, TimeUnit.MILLISECONDS);


    }
}
