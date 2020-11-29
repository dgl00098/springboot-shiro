package com.dgl.smodel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

/**
 * 账号表
 */
@Data
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames="user_account")})
@org.hibernate.annotations.Table(appliesTo = "user", comment = "账号表")
public class User extends BaseModel {

    @Column(name = "user_account", columnDefinition = ("varchar(50)  default null comment '用户账号'"))
    private String userAccount;

    @JsonIgnore
    @Column(name = "password", columnDefinition = ("varchar(255)  default null comment '用户密码'"))
    private String password;

    @Column(name = "mobile", columnDefinition = ("varchar(20)  default null comment '用户手机号'"))
    private String mobile;

    @Column(name = "salt", columnDefinition = ("int(11)  default null comment '随机盐'"))
    private int salt;

    @Column(name = "user_type", columnDefinition = ("tinyint(2)  default 0 comment '用户类型'"))
    private int userType;


    public User() {
    }

    public User(String userAccount, String userPwd, String userTel) {
        this.userAccount = userAccount;
        this.password = userPwd;
        this.mobile = userTel;
    }
}
