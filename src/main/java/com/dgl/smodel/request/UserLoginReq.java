package com.dgl.smodel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录请求体
 */
@Data
@ApiModel
public class UserLoginReq {

    @ApiModelProperty(value = "用户账号")
    @NotEmpty(message = "用户账号不能为空")
    private String mobilePhone;
    @ApiModelProperty(value = "登录密码")
    @NotEmpty(message = "用户密码不能为空")
    private String password;
    @ApiModelProperty(value = "登录终端A1-app  P1-PC")
    @NotEmpty(message = "登录终端不能为空")
    private String type;

    @ApiModelProperty(value = "用户id")
    private Integer userId;



}
