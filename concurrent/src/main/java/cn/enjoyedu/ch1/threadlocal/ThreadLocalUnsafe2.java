package cn.enjoyedu.ch1.threadlocal;

import cn.enjoyedu.tools.SleepTools;

/**
 * 类说明：ThreadLocal的线程不安全演示
 */
public class ThreadLocalUnsafe2 implements Runnable {

    //方式二： 不使用上面的变量，而直接使用threadLocal作为副本，每次创建ThreadLocal对象都初始化一个新的值
    private static ThreadLocal<Integer> safeThreadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

//    private static ThreadLocal<Integer> safeThreadLocal = new ThreadLocal<Integer>();
//    private Integer num = 1;



    public void run() {
//        safeThreadLocal.set(num);
        safeThreadLocal.set(safeThreadLocal.get() + 1);
        SleepTools.ms(2);
        System.out.println(Thread.currentThread().getName()+"="+safeThreadLocal.get());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafe2()).start();
        }
    }
}