package com.dgl.utils;

import cn.hutool.core.util.IdcardUtil;

/**
 * @className: Client
 * @Auther: DGL
 * @Date: 2020/04/12/18:51
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        String idcard="910923199104084853";
        String provinceByIdCard = IdcardUtil.getProvinceByIdCard(idcard);
        System.out.println(provinceByIdCard);
    }
}
