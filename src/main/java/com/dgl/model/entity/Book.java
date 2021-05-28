package com.dgl.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "book",uniqueConstraints = {@UniqueConstraint(columnNames="bookName")})
@org.hibernate.annotations.Table(appliesTo = "book", comment = "书籍表")
public class Book extends BaseModel {


    @Column(columnDefinition = ("varchar(40)  default '' comment '书籍名称' "))
    private String bookName;

    @Column(columnDefinition = ("varchar(40)  default '' comment '书籍作者' "))
    private String author;

    @Column(columnDefinition = ("varchar(400)  default '' comment '封面图' "))
    private String bookImage;

    @Column(columnDefinition = ("varchar(20)  default '' comment '书籍类型' "))
    private String bookType;

    @Column(columnDefinition = ("int(4)  default 0 comment '页数' "))
    private int bookPages;

    @Column(columnDefinition = ("varchar(100)  default '' comment '出版社' "))
    private String press;

    @Column(columnDefinition = ("varchar(10)  default '0.00' comment '价格' "))
    private String price;

}
