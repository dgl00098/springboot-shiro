package com.dgl.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 完善个人信息
 */
@Data
@ApiModel
public class CompleteInformationReq {

    @ApiModelProperty(value = "真实姓名")
    private String userName;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "年龄")
    private int age;

    @ApiModelProperty(value = "用户地址")
    private String userAddress;

    @ApiModelProperty(value = "用户邮箱")
    private String userEmail;

    @ApiModelProperty(value = "用户身份证号码")
    private String userIdCard;

    @ApiModelProperty(value = "个人介绍说明")
    private String selfIntroduction;


}
