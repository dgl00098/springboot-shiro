package com.dgl.smodel.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 角色表
 */
@Data
@Entity
@Table(name = "role",uniqueConstraints = {@UniqueConstraint(columnNames="name")})
@org.hibernate.annotations.Table(appliesTo = "role", comment = "角色表")
public class Role extends BaseModel {

    @Column(name = "name", columnDefinition = ("varchar(20)  default null comment '角色名称'"))
    private String name;


    public Role() {
    }

}
