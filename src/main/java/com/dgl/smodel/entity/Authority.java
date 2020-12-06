package com.dgl.smodel.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 权限表
 */
@Data
@Entity
@Table(name = "authority")
@org.hibernate.annotations.Table(appliesTo = "authority", comment = "权限表")
public class Authority extends BaseModel {

    @Column(name = "name", columnDefinition = ("varchar(20)  default null comment '权限名称'"))
    private String name;

    @Column(name = "pid", columnDefinition = ("bigint(11)  not null  comment '父级菜单id'"))
    private Integer pid;

    @Column(name = "auth_url", columnDefinition = ("varchar(255)  default null comment '权限地址(路径)'"))
    private String authUrl;


    public Authority() {
    }

}
