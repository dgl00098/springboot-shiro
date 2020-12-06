package com.dgl.smodel.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改密码请求体
 */
@Data
@ApiModel
public class ChangePasswordReq {

    @ApiModelProperty(value = "userId")
    @NotEmpty(message = "userId不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userAccount;

    @ApiModelProperty(value = "旧密码")
    @NotEmpty(message = "旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @NotEmpty(message = "新密码不能为空")
    private String newPwd;

    @ApiModelProperty(value = "确认新密码")
    @NotEmpty(message = "确认新密码不能为空")
    private String confirmNewPwd;


}
