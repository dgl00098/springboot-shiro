package com.dgl.smodel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户注册请求体
 */
@Data
@ApiModel
public class UserRegisterReq {

    @ApiModelProperty(value = "用户账号")
    @NotEmpty(message = "用户账号不能为空")
    private String userAccount;

    @ApiModelProperty(value = "登录密码")
    @NotEmpty(message = "用户密码不能为空")
    private String userPwd;

    @ApiModelProperty(value = "确认登录密码")
    @NotEmpty(message = "确认密码不能为空")
    private String confirmUserPwd;

    @ApiModelProperty(value = "用户手机号",notes = "找回密码时使用")
    @NotEmpty(message = "用户手机号不能为空")
    private String userTel;


}
