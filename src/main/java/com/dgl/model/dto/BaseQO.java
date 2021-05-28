package com.dgl.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName BaseQO
 * @Description 基础查询请求体的父类
 * @Author DGL
 * @Date 2020/12/6  16:42
 **/
@Data
public abstract class BaseQO {

    @ApiModelProperty(value = "当前页")
    protected int currentPage;

    @ApiModelProperty(value = "每页大小")
    protected int pageSize;

    @ApiModelProperty(value = "搜索关键字")
    protected String keywords;

}
