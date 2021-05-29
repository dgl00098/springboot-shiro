package com.dgl.model.dto;

import com.dgl.model.qo.BaseQueryQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 书籍列表请求体
 */
@Data
public class BookListDTO extends BaseQueryQO {

    @ApiModelProperty(value = "书籍名称")
    private String bookName;

    @ApiModelProperty(value = "书籍类型")
    private String bookType;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "状态")
    private int status;

    @ApiModelProperty(value = "书籍描述")
    private String description;

    @ApiModelProperty(value = "最低价格")
    private String minPrice;

    @ApiModelProperty(value = "最高价格")
    private String maxPrice;

}
