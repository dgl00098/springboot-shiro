package com.dgl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Random;

@Slf4j
public class ShiroMd5Util {


    /**
     * 对用户密码加密
     * @param password 密码原值
     * @param salt 随机盐
     * @return
     */
    public static String  SysMd5(String password,String salt) {
        SimpleHash hash = new SimpleHash("MD5",password,salt,1024);
        log.info("加密后密码：{}",hash.toString());
        return hash.toString();
    }

    /**
     * 生成随机盐的静态方法
     * @param n 随机盐的字符个数
     * @return
     */
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String s = ShiroMd5Util.SysMd5("123456","112");
        System.out.println(s);
    }
}
