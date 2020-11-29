package com.dgl.smodel.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章列表请求体
 */
@Data
public class ArticleListReq {

    @ApiModelProperty(value = "文章标题")
    private String tittle;

    @ApiModelProperty(value = "文章类型id")
    private Long typeId;

    //第几页从0开始
    private int page;

    //每页显示多少
    private int size;

    //排序方式
    private int sortType ;
}
