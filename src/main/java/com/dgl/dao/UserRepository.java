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
    User findByUserTel(String userTel);

    //根据用户账号查找用户
    User findByUserAccount(String userAccount);

    //根据用户账号和密码查找用户
    User findByUserAccountAndUserPwd(String userAccount,String userPwd);

    //根据用户账号和手机号查找用户
    User findByUserAccountAndUserTel(String userAccount,String userTel);

    //更新密码
    @Transactional
    @Modifying
    @Query(value = "update User u set u.userPwd =?1 where u.id=?2 ")
    void updatePassword(String pwd,Long userId);

    //更新用户的最后访问时间
    @Transactional
    @Modifying
    @Query(value = "update User u set u.lastAccessTime =?1 where u.id=?2 ")
    void updateUserLastAccessTime(LocalDateTime time ,Integer userId);
}
