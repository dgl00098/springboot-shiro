package com.dgl.common.Enum;

import lombok.Getter;
import lombok.ToString;

/**
 * 用户登录终端枚举
 */
@ToString
public enum EnumLoginType {

    APP("APP", "App端"),
    PC("PC", "PC端");

    @Getter
    private String code;

    @Getter
    private String type;

    EnumLoginType(String code, String type) {
        this.code = code;
        this.type = type;
    }
}
