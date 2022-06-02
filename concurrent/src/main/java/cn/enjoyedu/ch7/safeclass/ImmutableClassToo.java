package cn.enjoyedu.ch7.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 * 类不可变--事实不可变
 */
public class ImmutableClassToo {

    /**
     * 虽然list没有使用final修饰，但是在类之外看不到list变量，所以它是线程安全的类
     */
    private final List<Integer> list = new ArrayList<>(3);

    /**
     * 虽然此处是向list集合中添加元素，但是每创建一个对象，list成员变量都是属于this的，
     * 所以它是线程安全的。单例情况下只有一个实例，并且未提供任何改变list的方法，所以
     * 仍是线程安全的。
     */
    public ImmutableClassToo() {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    public boolean isContain(int i){
        return list.contains(i);
    }
}
