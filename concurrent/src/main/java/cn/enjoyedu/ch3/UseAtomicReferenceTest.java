package cn.enjoyedu.ch3;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *类说明：演示引用类型的原子操作类
 */
public class UseAtomicReferenceTest {
    static AtomicReference<UserInfo> atomicUserRef;
    static AtomicMarkableReference<UserInfo> amr = new AtomicMarkableReference<>(new UserInfo("james", 36), false);
    static AtomicStampedReference<UserInfo> asr = new AtomicStampedReference<>(new UserInfo("james", 36), 0);
    public static void main(String[] args) throws InterruptedException {
        UserInfo user = new UserInfo("Mark", 15);//要修改的实体的实例
        atomicUserRef = new AtomicReference(user);
        new Thread(new MyRunnable()).start();
        Thread.sleep(1000);
        UserInfo updateUser = new UserInfo("Bill",17);
        boolean isSuccess = atomicUserRef.compareAndSet(user, updateUser);

        System.out.println("It is successful that the main thread update the value : " + isSuccess);

        System.out.println(atomicUserRef.get());
        System.out.println(user);
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("The sub thread is running...");
            UserInfo userInfo = atomicUserRef.get();
            atomicUserRef.compareAndSet(userInfo,new UserInfo("james", 36));
//            UserInfo userInfo1 = atomicUserRef.get();
//            userInfo.setName("curry");
//            atomicUserRef.compareAndSet(userInfo1,userInfo);
        }
    }
    
    //定义一个实体类
    static class UserInfo {
        private volatile String name;
        private int age;
        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
