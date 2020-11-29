package com.dgl.smodel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@ApiModel(value = "流程部署请求体")
public class DeployProcessReq {


    @ApiModelProperty(value = "流程名字",required = true)
    private String name;

    @ApiModelProperty(value = "流程的key",notes = "一定要保证全局唯一",required = true)
    private String key;

    @ApiModelProperty(value = "流程分类")
    private String category;

    @ApiModelProperty(value = "BPMN资源路径")
    private String classpathResourceBPMN;

    @ApiModelProperty(value = "zip资源路径",required = true)
    private String classpathResourceZIP;

    @ApiModelProperty(value = "PNG资源路径")
    private String classpathResourcePNG;

}
