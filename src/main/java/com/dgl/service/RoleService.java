package com.dgl.service;

import com.dgl.model.entity.Role;
import com.dgl.model.dto.BaseQO;
import com.dgl.model.vo.RespEntity;
import org.springframework.data.domain.Page;

/**
 * 角色管理
 */
public interface RoleService {


    Role save(String roleName);

    Page<Role> list(BaseQO baseQO);

    void delete(Integer id);

    void update(Integer id, String name);

    RespEntity list();
}
