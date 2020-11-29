package cn.tzecc.common.Enum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户状态枚举
 */
@ToString
public enum UserStatusEnum {

    normal(0, "正常"),
    freeze(1, "冻结"),
    RetrievingPassword(2, "密码找回中...."),
    Logout(3, "注销")
    ;
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String status;

    UserStatusEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
