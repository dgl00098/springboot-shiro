package com.dgl.smodel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 新增文章请求体
 */
@Data
@ApiModel
public class AddArticleReq {

    @ApiModelProperty(value = "userId")
    @NotNull(message = "创建人不能为空")
    private Long userId;

    @ApiModelProperty(value = "文章标题")
    @NotEmpty(message = "文章标题不能为空")
    private String tittle;

    @ApiModelProperty(value = "文章内容")
    @NotEmpty(message = "文章内容不能为空")
    private String content;

    @ApiModelProperty(value = "文章图片url")
    private String imageUrl;

    @ApiModelProperty(value = "文章类型ID")
    @NotNull(message = "文章类型不能为空")
    private Long articleTypeId;

    @ApiModelProperty(value = "文章标签(原创/转载)")
    @NotEmpty(message = "文章标签不能为空")
    private String tag;

}
