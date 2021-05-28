package com.dgl.model.entity;


import com.dgl.common.utils.DGLDateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 所有类的超类
 * 自动更新创建时间和更新时间
 * @author peter
 *
 **/
@MappedSuperclass
@Data
public abstract class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @CreatedDate
    @Column(name = "create_time",columnDefinition="datetime not null COMMENT '创建时间'", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DGLDateUtils.FORMAT_LONG)
    protected LocalDateTime createTime=LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "update_time",columnDefinition="timestamp default CURRENT_TIMESTAMP COMMENT '最后更新时间'")
    @DateTimeFormat(pattern = DGLDateUtils.FORMAT_LONG)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DGLDateUtils.FORMAT_LONG)
    protected LocalDateTime updateTime;

    @Column(columnDefinition = "TINYINT(1) unsigned COMMENT '状态(0正常 1已删除)'", nullable = false)
    protected int status;

}
