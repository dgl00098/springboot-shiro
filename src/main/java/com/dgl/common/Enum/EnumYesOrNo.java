package com.dgl.common.Enum;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum EnumYesOrNo {
    YES(1,"是"),
    NO(0,"否");
    private Integer code;
    private String value;

    EnumYesOrNo(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态获取code
     * @param value
     * @return
     */
    public static EnumYesOrNo getByValue(String value){
        if (StringUtils.isEmpty(value)){
            return YES;
        }
        return Arrays.stream(values()).filter(s->s.getValue().equals(value)).findFirst().get();
    }

    /**
     * 根据状态获取code
     * @param code
     * @return
     */
    public static EnumYesOrNo getByCode(int code){
        return Arrays.stream(values()).filter(s->s.getCode().equals(code)).findFirst().get();
    }

}