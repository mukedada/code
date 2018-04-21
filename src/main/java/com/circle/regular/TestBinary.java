package com.circle.regular;

/**
 * @author KeWeiYang
 * @date 2018/4/17 下午7:56
 * TODO
 */
public class TestBinary {
    public static void main(String[] args) {
        int sum=0;
        int step = 1;
        for (int i = 0; i < 100; i++) {
            sum += step;

        }
        System.out.println(sum/10);

    }
}
