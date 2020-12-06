package com.dgl.smodel.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 忘记密码请求体
 * 用户找回密码
 */
@Data
@ApiModel
public class RetrievePasswordReq {

    @ApiModelProperty(value = "userId")
    @NotEmpty(message = "userId不能为空")
    private Long userId;

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
