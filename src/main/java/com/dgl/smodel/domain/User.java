package com.dgl.smodel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

/**
 * 账号表
 */
@Data
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames="mobile")})
@org.hibernate.annotations.Table(appliesTo = "user", comment = "账号表")
public class User extends BaseModel {

    @Column(name = "mobile", columnDefinition = ("varchar(20)  default null comment '用户手机号'"))
    private String mobile;

    @Column(name = "user_type", columnDefinition = ("tinyint(2)  default 0 comment '用户类型 0普通用户 '"))
    private int userType;

    @JsonIgnore
    @Column(name = "password", columnDefinition = ("varchar(255)  default null comment '用户密码'"))
    private String password;

    @JsonIgnore
    @Column(name = "salt", columnDefinition = ("varchar(20)  default null comment '随机盐'"))
    private String salt;


    public User() {
    }

    public User(String userPwd, String userTel,int userType,String salt) {
        this.password = userPwd;
        this.mobile = userTel;
        this.userType = userType;
        this.salt = salt;
    }
}
