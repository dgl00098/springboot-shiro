package com.dgl.smodel.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 更新文章请求体
 */
@Data
@ApiModel
public class UpdateArticleReq {

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "文章标题")
    @NotEmpty(message = "文章标题不能为空")
    private String tittle;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章图片url")
    private String imageUrl;

    @ApiModelProperty(value = "文章类型ID")
    private Long articleTypeId;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "文章更新时间")
    private LocalDateTime updateTime;

    public LocalDateTime getUpdateTime(){
        return LocalDateTime.now();
    }

}
