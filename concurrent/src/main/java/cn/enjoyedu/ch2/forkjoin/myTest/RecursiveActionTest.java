package cn.enjoyedu.ch2.forkjoin.myTest;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @Description 无反应，待解决
 * @ProjectName vip-v2-concurrent
 * @Package cn.enjoyedu.ch2.forkjoin.myTest
 * @Classname RecursiveActionTest
 * @Author DengSenyang
 * @CreateDate 2021/11/30 9:41
 */
public class RecursiveActionTest {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(500);
        MyRecursiveAction task = new MyRecursiveAction(new File("E:/"));
        pool.execute(task);
    }
//这是一段测试代码
}
