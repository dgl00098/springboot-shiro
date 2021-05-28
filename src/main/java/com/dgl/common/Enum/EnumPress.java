package com.dgl.common.Enum;

import cn.hutool.core.util.RandomUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 出版社枚举
 */
@Getter
public enum EnumPress {
    A(1,"电子工业出版社"),
    B(2,"人民日报出版社"),
    C(3,"青年出版社"),
    D(4,"北京邮电大学"),
    E(5,"北京出版社"),
    F(6,"北京工业大学出版社"),
    G(7,"北京教育出版社"),
    H(8,"北京旅游出版社"),
    I(9,"北京理工大学出版社");

    private Integer code;
    private String value;

    EnumPress(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态获取code
     * @param value
     * @return
     */
    public static EnumPress getByValue(String value){
        if (StringUtils.isEmpty(value)){
            return A;
        }
        return Arrays.stream(values()).filter(s->s.getValue().equals(value)).findFirst().get();
    }

    /**
     * 根据状态获取code
     * @param code
     * @return
     */
    public static EnumPress getByCode(int code){
        return Arrays.stream(values()).filter(s->s.getCode().equals(code)).findFirst().get();
    }

    /**
     * 随机获得出版社
     * @return
     */
    public static EnumPress getByRandom(){
        int randomInt = RandomUtil.randomInt(1, 10);

        EnumPress enumPress = getByCode(randomInt);
        return enumPress;
    }

    public static void main(String[] args) {
        char randomChar = getRandomChar();
        System.out.println(randomChar);
    }
    public static char getRandomChar() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }
}