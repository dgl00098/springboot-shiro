package com.dgl.common.utils;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName Test
 * @Description 计算2个字符串相似度
 * @Author DGL
 * @Date 2021/5/24  19:59
 **/
public class MyTest {

    public static void main(String[] args) {
        //要比较的两个字符串
        String str1 = "今天星期四";
        String str2 = "今天星期四1";
        levenshtein(str1,str2);
        jaccard(str1,str2);
    }


    public static void levenshtein(String str1,String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        System.out.println("差异步骤："+dif[len1][len2]);
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        System.out.println("方法1的相似度："+similarity);
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

    public static float jaccard(String a, String b) {

        Set<Integer> result = new HashSet<Integer>();
        if (a == null && b == null) {
            return 1f;
        }
        // 都为空相似度为 1
        if (a == null || b == null) {
            return 0f;
        }
        Set<Integer> aChar = a.chars().boxed().collect(Collectors.toSet());
        Set<Integer> bChar = b.chars().boxed().collect(Collectors.toSet());
        // 交集数量
        result.clear();
        result.addAll(bChar);
        result.retainAll(bChar);
        int intersection =result.size();
        if (intersection == 0) return 0;
        // 并集数量
        result.clear();
        result.addAll(aChar);
        result.addAll(bChar);
        float v = ((float) intersection) / (float) result.size();
        System.out.println("方法2的相似度："+v);
        return v;
    }
}
