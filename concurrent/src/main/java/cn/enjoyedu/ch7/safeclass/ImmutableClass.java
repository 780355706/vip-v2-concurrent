package cn.enjoyedu.ch7.safeclass;

/**
 * 类不可变
 */
public class ImmutableClass {
    private final int a;
    private final int b;
    private final UserVo user = new UserVo();//不安全(多个线程对同一个ImmutableClass实例中的user对象中的属性进行修改，多实例情况下则是线程安全的)

	public int getA() {
		return a;
	}

	public UserVo getUser() {
		return user;
	}


	public ImmutableClass(int a) {
		this.a = a;
		this.b = a;
	}

	public static class User{
    	private int age;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
    }
}
