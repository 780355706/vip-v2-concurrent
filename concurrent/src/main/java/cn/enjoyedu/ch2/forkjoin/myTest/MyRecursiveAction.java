package cn.enjoyedu.ch2.forkjoin.myTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @Description TODO
 * @ProjectName vip-v2-concurrent
 * @Package cn.enjoyedu.ch2.forkjoin.myTest
 * @Classname MyRecursiveAction
 * @Author DengSenyang
 * @CreateDate 2021/11/30 10:22
 */
public class MyRecursiveAction extends RecursiveAction {
    private File myFile;
    public MyRecursiveAction(File myFile) {
        this.myFile = myFile;
    }
    @Override
    protected void compute() {
        if (myFile.isDirectory()) {
            List<MyRecursiveAction> subTasks = new ArrayList<>();
            File[] files = myFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    MyRecursiveAction myRecursiveAction = new MyRecursiveAction(file);
                    subTasks.add(myRecursiveAction);
                } else {
                    boolean b = file.getAbsolutePath().endsWith(".txt");
                    if (b) System.out.println(file.getAbsolutePath());
                }
            }
            if (!subTasks.isEmpty()) {
                for (MyRecursiveAction myRecursiveAction : invokeAll(subTasks)) {
                    myRecursiveAction.join();
                }
            }
        }
    }
}
