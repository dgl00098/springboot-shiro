package com.dgl.common.Enum;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by captain on 2017/6/2.
 */
public enum EnumErrorMsg {
    OP_SUCCESS("200", "操作成功"),
    SYS_BUSY("500", "服务器忙,稍后重试......"),
    OP_FAILED("400", "操作失败"),
    NO_REPEATED_SUBMIT("500", "请不要重复提交数据!!!"),

    //用户相关
    PWD_NOT_EQUALS("1000","两次密码输入不一致,请核对后重新输入!"),
    TEL_HAS_EXIST("1001","该手机号已经注册!"),
    USER_ACCOUNT_HAS_EXIST("1002","该账号已经注册!"),
    USER_ACCOUNT_NOT_EXIST("1004","该账号不存在"),
    REGISTER_SUCCESS("1005","恭喜您,注册成功!请尽快完善个人信息!"),
    USER_TEL_NOT_EXIST("1006","对不起,该手机号不存在,请核对后再输!"),
    USER_PWD_ERROR("1007","输入的旧密码错误,请核对后再输!"),
    OLD_AND_NEW_PWD_EQUALS("1008","新密码不能与旧密码相同"),
    USER_SESSION_ERROR("1009", "用户未登录！"),
    EMAIL_SEND_SUCCESS("1010", "邮件发送成功!"),
    VERIFICATION_CODE_ERROR("1011", "验证码错误!"),
    VERIFICATION_CODE_EXPIRE("1012", "验证码已过期!"),
    EMAIL_BIND_SUCCESS("1013", "用户邮箱绑定成功!"),
    PASSWORD_ERROR("1003","密码错误!"),
    TEXT_IS_BLANK("1004","请填写完整信息!"),

    //文章相关
    ARTICLE_HAS_EXIST("2000","文章已存在!"),
    ARTICLE_TYPE_HAS_EXIST("2001","文章类型已存在!"),
    ARTICLE_Not_EXIST("2002","文章不存在!"),
    TEXT_ERROR("2003","文本内容非法!"),

    //上传文件相关
    FILE_NOT_SUPPORT("3000","文件格式不支持")
    ;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String msg;

    EnumErrorMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
