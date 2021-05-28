package com.dgl.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户角色表
 */
@Data
@Entity
@Table(name = "user_role")
@org.hibernate.annotations.Table(appliesTo = "user_role", comment = "用户角色表")
public class UserRole extends BaseModel {

    @Column(name = "user_id", columnDefinition = ("bigint(11)  not null comment '用户id'"))
    private Integer userId;

    @Column(name = "role_id", columnDefinition = ("bigint(11)  not null comment '角色id'"))
    private Integer roleId;


    public UserRole() {
    }

}
