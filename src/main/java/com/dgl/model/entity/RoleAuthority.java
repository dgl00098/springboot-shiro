package com.dgl.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色权限表
 */
@Data
@Entity
@Table(name = "role_authority")
@org.hibernate.annotations.Table(appliesTo = "role_authority", comment = "角色权限表")
public class RoleAuthority extends BaseModel {

    @Column(name = "auth_id", columnDefinition = ("bigint(11)  not null comment '权限id'"))
    private Integer authId;

    @Column(name = "role_id", columnDefinition = ("bigint(11)  not null comment '角色id'"))
    private Integer roleId;


    public RoleAuthority() {
    }

}
