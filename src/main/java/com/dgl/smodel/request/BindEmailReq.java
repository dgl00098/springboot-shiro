package com.dgl.smodel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * bind邮箱请求体
 */
@Data
@ApiModel
public class BindEmailReq {

    @ApiModelProperty(value = "邮箱地址")
    private String userEmail;

    @ApiModelProperty(value = "验证码")
    private int verificationCode;

    @ApiModelProperty(value = "验证码类型")
    private int verificationCodeType;


}
