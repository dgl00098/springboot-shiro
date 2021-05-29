package com.dgl.model.entity.es;

import com.dgl.common.utils.DGLDateUtils;
import com.dgl.model.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Document(indexName = "index_book")
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    private String id;

    private String createTime;

    private int status;

    @Field(analyzer = "ik_smart", type = FieldType.Keyword)
    private String bookName;

    @Field(analyzer = "ik_smart", type = FieldType.Keyword)
    private String author;

    private String bookImage;

    @Field(analyzer = "ik_smart", type = FieldType.Keyword)
    private String bookType;

    private int bookPages;

    private String press;

    @Field(analyzer = "ik_smart", type = FieldType.Keyword)
    private String description;

    private String price;

    //新增
    public Book(BookDTO dto) {
        this.bookName = dto.getBookName();
        this.author = dto.getAuthor();
        this.bookImage = dto.getBookImage();
        this.bookType = dto.getBookType();
        this.bookPages = dto.getBookPages();
        this.press = dto.getPress();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
        this.createTime =DGLDateUtils.ldtToString(LocalDateTime.now(),DGLDateUtils.FORMAT_LONG);
    }
}
