package cn.tzecc.common.Enum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 邮箱验证码类型枚举
 */
@ToString
public enum EmailOperateTypeEnum {

    BindMailbox(0, "绑定邮箱"),
    ChangeTel(1, "更换手机号"),
    ResetPassword(2, "重置密码")
    ;
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String status;

    EmailOperateTypeEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
