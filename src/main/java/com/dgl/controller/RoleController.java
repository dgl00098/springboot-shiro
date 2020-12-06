package com.dgl.controller;

import com.dgl.service.RoleService;
import com.dgl.smodel.entity.Role;
import com.dgl.smodel.qo.BaseQO;
import com.dgl.smodel.vo.RespEntity;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;


@RequestMapping(value = "/role")
@RestController
@Api(tags = {"角色管理"})
@ApiSupport(order = 2, author = "杜光磊")
public class RoleController {

    @Autowired
    RoleService roleService;


    @ApiOperation(value = "添加角色")
    @PostMapping(value = "/save")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParam(name = "roleName", value = "角色名称", required = true)
    public RespEntity save(String roleName) {
        Role role = roleService.save(roleName);
        return new RespEntity(role);
    }

    @ApiOperation(value = "所有角色")
    @GetMapping(value = "/list")
    @ApiOperationSupport(order = 2)
    public RespEntity list() {
        return roleService.list();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/update")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "name", value = "角色名称", required = true)
    })
    public RespEntity update(Integer id, String name) {
        roleService.update(id, name);
        return new RespEntity();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "/delete")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    public RespEntity delete(Integer id) {
        roleService.delete(id);
        return new RespEntity();
    }

}
