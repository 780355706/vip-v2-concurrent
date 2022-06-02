package cn.enjoyedu.ch3.mytest;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class UseAtomicStampedReference {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("james", 37);
        UserInfo updateInfo = new UserInfo("cobe", 45);
        AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(userInfo, true);
        boolean flag = atomicMarkableReference.compareAndSet(userInfo, updateInfo, true, false);
        System.out.println(flag);
    }




    //定义一个实体类
    static class UserInfo {
        private volatile String name;
        private int age;
        public UserInfo(String name, int age) {
            this.name = name;
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
