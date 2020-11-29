package com.dgl.smodel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 用户登录请求体
 */
@Data
@ApiModel
public class SendEmailReq {

    @ApiModelProperty(value = "邮箱地址")
    private String userEmail;

    @ApiModelProperty(value = "验证码类型")
    private int verificationCodeType;


}
