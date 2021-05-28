package com.dgl.common.Enum;

import lombok.Getter;

/**
 * 书籍类型枚举
 */
@Getter
public enum EnumBookType {

    BindMailbox(0, "绑定邮箱"),
    ChangeTel(1, "更换手机号"),
    ResetPassword(2, "重置密码")
    ;

    private int code;

    private String type;

    EnumBookType(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
