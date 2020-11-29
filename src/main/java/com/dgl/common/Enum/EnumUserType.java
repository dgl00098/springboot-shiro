package com.dgl.common.Enum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户类型枚举
 */
@ToString
public enum EnumUserType {

    A1("A1", "App办学人"),
    A2("A2", "App教师/校长"),
    A4("A4", "App家长"),
    A5("A5", "小程序教师端"),
    A6("A6", "小程序家长端"),
    A7("A7", "快教务App-教师端"),
    A8("A8", "快教务App-家长端"),
    P1("P1", "PC办学人后台"),
    P2("P2", "PC育幼通后台"),
    P3("P3", "PC快教务后台");

    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String type;

    EnumUserType(String code, String type) {
        this.code = code;
        this.type = type;
    }
}
