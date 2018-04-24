package com.circle.algorithm;

import java.util.Random;

/**
 * @author KeWeiYang
 * @date 2018/4/24 16:30
 * 排序帮助类
 */
public class SortHelper {

    /**
     * 生成有n个元素的随机数组，每个元素的随机范围[rangeL,rangeR]
     * @param n
     * @param rangeL
     * @param rangeR
     * @return
     */
    public int[] generateRandowmArray(int n, int rangeL, int rangeR) {

        if (rangeL > rangeR) {
            throw new RuntimeException("索引范围出错！");
        }
        int[] arr = new int[n];
        // 设置随机种子，以当前时间为随机种子
        Random random = new Random(System.currentTimeMillis());
        for(int i=0;i<n;i++) {
            // 生成随机数
            arr[i] = random.nextInt(rangeR - rangeL )  + rangeL;
        }
        return arr;
    }

    public void printArray(int[] arr, int n) {
        for(int i=0;i<n;i++) {
            System.out.println(arr[i] + " ");

        }
    }
    public static void main(String[] args) {
        SortHelper helper = new SortHelper();
        int n = 10;
        int[] arr = helper.generateRandowmArray(n, 0, n);
        helper.printArray(arr, n);
    }
}
