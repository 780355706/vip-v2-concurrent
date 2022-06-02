package cn.enjoyedu.ch2.forkjoin.sum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MakeArray {
    //数组长度
    public static final int ARRAY_LENGTH  = 400000000;
    public final static int THRESHOLD = 47;

    public static int[] makeArray() {

        //new一个随机数发生器
        Random r = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for(int i=0;i<ARRAY_LENGTH;i++){
            //用随机数填充数组
            result[i] =  r.nextInt(ARRAY_LENGTH*3);
        }
        return result;

    }

    public static List<Integer> makeIntegerList() {

        //new一个随机数发生器
        Random r = new Random();
        List<Integer> list = new ArrayList<Integer>(ARRAY_LENGTH);
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            list.add(r.nextInt(ARRAY_LENGTH*3));
        }
        return list;

    }
}
