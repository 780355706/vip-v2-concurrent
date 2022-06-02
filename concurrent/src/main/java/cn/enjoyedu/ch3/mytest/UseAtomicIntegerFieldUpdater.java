package cn.enjoyedu.ch3.mytest;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class UseAtomicIntegerFieldUpdater {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo(1, "james");
        AtomicIntegerFieldUpdater<UserInfo> updater = AtomicIntegerFieldUpdater.newUpdater(UserInfo.class, "id");
        boolean flag = updater.compareAndSet(userInfo, 1, 2);
        System.out.println(flag);
        System.out.println(userInfo.id);


    }


    private static class UserInfo {
        public volatile int id;
        public volatile String name;

        public UserInfo() {
        }

        public UserInfo(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
