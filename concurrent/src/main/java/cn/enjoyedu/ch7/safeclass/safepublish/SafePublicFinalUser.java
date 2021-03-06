package cn.enjoyedu.ch7.safeclass.safepublish;

/**
 * 类说明：委托给线程安全的类来做(拿到的只是jar包，而jar包中的类是被final修饰的，不能被继承，那么就要使用委托的方式来实现)
 */
public class SafePublicFinalUser {
    private final SynFinalUser user;

    public SynFinalUser getUser() {
        return user;
    }

    public SafePublicFinalUser(FinalUserVo user) {
        this.user = new SynFinalUser(user);
    }

    public static class SynFinalUser{
        private final FinalUserVo userVo;
        private final Object lock = new Object();

        public SynFinalUser(FinalUserVo userVo) {
            this.userVo = userVo;
        }

        public int getAge() {
            synchronized (lock){
                return userVo.getAge();
            }
        }

        public void setAge(int age) {
            synchronized (lock){
                userVo.setAge(age);
            }
        }
    }

}
