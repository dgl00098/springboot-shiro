package com.dgl.smodel.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章评论请求体
 */
@Data
public class CommentArticleReq {

    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "评论内容")
    private String comments;

}
