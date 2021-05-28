package com.dgl.dao;

import com.dgl.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 角色dao
 */
@Service
public interface RoleRepository extends JpaRepository<Role, Integer> {


    @Query(value = "select r.name from Role r where r.name like %?1% and r.status=0")
    Page<Role> findByName(String name, Pageable pageable);


    @Query("update Role r set r.status=1 where r.id=?1")
    @Transactional
    @Modifying
    int deleteRoleById(Integer id);


    @Query("update Role r set r.name=?2 where r.id=?1")
    @Transactional
    @Modifying
    int updateById(Integer id,String name);
}
