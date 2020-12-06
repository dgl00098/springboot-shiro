package com.dgl.smodel.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 所有类的超类
 * 自动更新创建时间和更新时间
 * @author peter
 *
 **/
@MappedSuperclass
@Data
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_time",columnDefinition="datetime not null COMMENT '创建时间'", nullable = false, updatable = false)
    protected LocalDateTime createTime=LocalDateTime.now();

    @Column(name = "update_time",columnDefinition="timestamp default CURRENT_TIMESTAMP COMMENT '最后更新时间'")
    protected LocalDateTime updateTime;

    @Column(columnDefinition = "TINYINT(1) unsigned COMMENT '状态(0正常 1已删除)'", nullable = false)
    protected int status;

}
