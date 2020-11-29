package com.dgl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @ClassName ShiroMd5Util
 * @Description: TODO
 * @Author 张开磊
 * @Date 2020/5/27
 * @Version V1.0
 **/
@Slf4j
public class ShiroMd5Util {
    //添加user的密码加密方法
    public static String  SysMd5(String password) {
        String hashAlgorithmName = "MD5";//加密方式

        Object crdentials =password;//密码原值

        //ByteSource salt = ByteSource.Util.bytes("user");//以user作为盐值

        int hashIterations = 1;//加密1024次

        SimpleHash hash = new SimpleHash(hashAlgorithmName,crdentials,"",hashIterations);
        log.info("加密后密码：{}",hash.toString());
        return hash.toString();
    }

    public static void main(String[] args) {
        String s = ShiroMd5Util.SysMd5("123456");
        System.out.println(s);
    }
}
