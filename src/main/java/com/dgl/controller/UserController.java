package com.dgl.controller;

import com.dgl.service.UserService;
import com.dgl.model.entity.User;
import com.dgl.model.dto.ChangePasswordReq;
import com.dgl.model.dto.RetrievePasswordReq;
import com.dgl.model.dto.UserLoginReq;
import com.dgl.model.dto.UserRegisterReq;
import com.dgl.model.vo.RespEntity;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RequestMapping(value = "/user")
@RestController
@Api(tags = {"用户管理"})
@ApiSupport(order = 1,author = "杜光磊")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册",notes = "用户注册")
    @PostMapping(value = "/register")
    @ApiOperationSupport(order = 1)
    public RespEntity<?> userRegister(@RequestBody @Validated UserRegisterReq req){
        User user = userService.userRegister(req);
        return RespEntity.success(user);
    }

    /**
     * 用户登录
     */
    @ApiOperation(value = "用户登录",notes = "用户登录")
    @PostMapping(value = "/login")
    @ApiOperationSupport(order = 2)
    public RespEntity<?> login(@RequestBody @Validated UserLoginReq req){
        Map<String, Object> map= userService.userLogin(req);
        return RespEntity.success(map);
    }

    /**
     * 注销登录
     */
    @ApiOperation(value = "注销登录",notes = "注销登录")
    @GetMapping(value = "/logout")
    public RespEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        return userService.logout(request,response);
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码",notes = "用户修改密码")
    @PostMapping(value = "/changePassword")
    public RespEntity<?> changePassword(@RequestBody @Validated ChangePasswordReq req){
        Integer result= userService.changePassword(req);
        return RespEntity.success(result);
    }

    /**
     * 忘记密码
     */
    @ApiOperation(value = "忘记密码",notes = "用户找回密码")
    @PostMapping(value = "/retrievePassword")
    public RespEntity<?> retrievePassword(@RequestBody @Validated RetrievePasswordReq req){
        Integer result= userService.retrievePassword(req);
        return RespEntity.success(result);
    }


}
