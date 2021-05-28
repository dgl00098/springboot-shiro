package com.dgl.dao;

import com.dgl.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 用户dao
 */
@Service
public interface UserRepository extends JpaRepository<User,Integer> {


    /**
     * 根据手机号查询用户
     * @param userTel 手机号码
     * @return
     */
    Optional<User> findByMobile(String userTel);

    /**
     * 根据手机号和密码查询用户
     * @param userTel 手机号码
     * @param password 密码
     * @return
     */
    Optional<User> findByMobileAndPassword(String userTel,String password);

    //更新密码
    @Transactional
    @Modifying
    @Query(value = "update User u set u.password =?1 where u.id=?2 ")
    int updatePassword(String pwd,Long userId);

}
