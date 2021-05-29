package com.dgl.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName BaseQueryQO
 * @Description 基础查询请求体的父类
 * @Author DGL
 * @Date 2020/12/6  16:42
 **/
@Data
public abstract class BaseQueryQO {

    @ApiModelProperty(value = "当前页")
    protected int currentPage;

    @ApiModelProperty(value = "每页大小")
    protected int pageSize=10;

    @ApiModelProperty(value = "排序字段")
    protected String orderField;

    @ApiModelProperty(value = "排序方式")
    protected String orderType;

    @ApiModelProperty(value = "搜索关键字")
    protected String keywords;

}
