package com.dgl.dao;

import com.dgl.smodel.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * 用户dao
 */
@Service
public interface UserRepository extends CrudRepository <User,Integer> {

    User findUserById(Long userId);

    //根据手机号查找用户
    User findByMobile(String userTel);

    //更新密码
    @Transactional
    @Modifying
    @Query(value = "update User u set u.password =?1 where u.id=?2 ")
    void updatePassword(String pwd,Long userId);

}
