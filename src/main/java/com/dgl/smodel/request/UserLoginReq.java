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
    private String userAccount;

    @ApiModelProperty(value = "登录密码")
    @NotEmpty(message = "用户密码不能为空")
    private String userPwd;


}
