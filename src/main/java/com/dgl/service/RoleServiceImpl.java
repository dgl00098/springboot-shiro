package com.dgl.service;

import com.dgl.dao.RoleRepository;
import com.dgl.smodel.entity.Role;
import com.dgl.smodel.qo.BaseQO;
import com.dgl.smodel.vo.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author DGL
 * @Date 2020/12/6  16:25
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role save(String roleName) {
        Role role = new Role(roleName);
        Role save = roleRepository.save(role);
        return save;
    }

    @Override
    public Page<Role> list(BaseQO req) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        Pageable pageable = PageRequest.of(req.getCurrentPage(), req.getPageSize(),sort);
        Page<Role> page = roleRepository.findByName(req.getKeywords(), pageable);
        return page;
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteRoleById(id);
    }

    @Override
    public void update(Integer id, String name) {
       roleRepository.updateById(id, name);
    }

    @Override
    public RespEntity list() {
        List<Role> roleList = roleRepository.findAll();
        return new RespEntity(roleList);
    }
}
