package com.dgl.common.Enum;

import cn.hutool.core.util.RandomUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 书籍类型枚举
 */
@Getter
public enum EnumBookType {

    A(0, "军事"),
    B(1, "科技"),
    C(2, "数码"),
    D(3, "情感"),
    E(4, "财经"),
    F(5, "小说"),
    G(6, "名著"),
    H(7, "搞笑"),
    I(8, "两性"),
    J(9, "漫画"),
    K(10, "儿童"),
    L(11, "房产"),
    ;

    private int code;

    private String type;

    EnumBookType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    /**
     * 根据状态获取code
     * @param type
     * @return
     */
    public static EnumBookType getByValue(String type){
        if (StringUtils.isEmpty(type)){
            return A;
        }
        return Arrays.stream(values()).filter(s->s.getType().equals(type)).findFirst().get();
    }

    /**
     * 根据状态获取code
     * @param code
     * @return
     */
    public static EnumBookType getByCode(int code){
        return Arrays.stream(values()).filter(s->s.getCode()==(code)).findFirst().get();
    }

    /**
     * 随机获得出版社
     * @return
     */
    public static EnumBookType getByRandom(){
        int randomInt = RandomUtil.randomInt(1, 12);
        EnumBookType byCode = getByCode(randomInt);
        return byCode;
    }
}
