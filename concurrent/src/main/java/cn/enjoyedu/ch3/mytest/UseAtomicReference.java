package cn.enjoyedu.ch3.mytest;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("james", 36);
        AtomicReference<UserInfo> first = new AtomicReference<>(userInfo);
        UserInfo updateInfo = new UserInfo("cobe", 45);
        boolean flag1 = first.compareAndSet(userInfo, updateInfo);
        boolean flag2 = first.compareAndSet(updateInfo, userInfo);
        boolean flag3 = first.compareAndSet(userInfo, updateInfo);
        boolean flag4 = first.compareAndSet(updateInfo, userInfo);
        System.out.println(flag1);
        System.out.println(flag2);
        System.out.println(flag3);
        System.out.println(flag4);
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
