package com.dgl.model.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.dgl.model.entity.es.Book;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
@ApiModel(description = "书籍")
@Builder
@AllArgsConstructor
public class BookDTO {


    @ApiModelProperty(value = "书籍名称")
    @NotEmpty(message = "书籍名称不能为空")
    @ExcelProperty(value = "书籍名称")
    private String bookName;

    @ApiModelProperty(value = "作者")
    @NotEmpty(message = "书籍作者不能为空")
    @ExcelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "封面图")
    @ExcelIgnore
    private String bookImage;

    @ApiModelProperty(value = "书籍类型")
    @NotNull(message = "书籍类型不能为空")
    @ExcelProperty(value = "书籍类型")
    private String bookType;

    @ApiModelProperty(value = "页数")
    @Size(min = 10,message = "页数大小超出范围限制")
    @ExcelProperty(value = "页数")
    private int bookPages;

    @ApiModelProperty(value = "出版社")
    @NotEmpty(message = "出版社不能为空")
    @ExcelProperty(value = "出版社")
    private String press;

    @ApiModelProperty(value = "价格")
    @ExcelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "创建时间")
    private String createTime ="";

    public BookDTO() {
    }

    public BookDTO(Book book) {
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.bookImage = book.getBookImage();
        this.bookType = book.getBookType();
        this.bookPages = book.getBookPages();
        this.press = book.getPress();
        this.price = book.getPrice();
        this.createTime = book.getCreateTime();
    }
}
