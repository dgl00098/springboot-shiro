package com.dgl.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 完善个人信息
 */
@Data
@ApiModel
public class UserInformationResp {

    @ApiModelProperty(value = "userId")
    @NotEmpty(message = "userId不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户手机号")
    private String userTel;

    @ApiModelProperty(value = "用户注册时间")
    private LocalDateTime registerTime;

    @ApiModelProperty(value = "用户状态")
    private int userStatus;

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

    public UserInformationResp() {
    }


}
