package com.dgl.smodel.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel(value = "用户注册请求体")
public class UserRegisterReq {

    @ApiModelProperty(value = "登录密码")
    @NotBlank(message = "用户密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "用户手机号",notes = "找回密码时使用")
    @NotBlank(message = "用户手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "用户类型0老师 1学生",hidden = true)
    private int userType;

}
