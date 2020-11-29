package com.dgl.controller;

import com.alibaba.fastjson.JSON;
import com.dgl.config.shiro.CommonUtil;
import com.dgl.service.RegisterService;
import com.dgl.smodel.request.ChangePasswordReq;
import com.dgl.smodel.request.RetrievePasswordReq;
import com.dgl.smodel.request.UserLoginReq;
import com.dgl.smodel.request.UserRegisterReq;
import com.dgl.smodel.response.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册管理
 */
@RequestMapping(value = "/register")
@RestController
@Api(tags = {"注册管理"},value = "注册管理&登录页面")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册",notes = "用户注册")
    @PostMapping(value = "/userRegister")
    public RespEntity<?> userRegister(@RequestBody @Validated UserRegisterReq req){
        return registerService.userRegister(req);
    }

//    /**
//     * 用户登录
//     */
//    @ApiOperation(value = "用户登录",notes = "用户登录")
//    @PostMapping(value = "/login")
//    public RespEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody @Validated UserLoginReq req){
//        return registerService.userLogin(request,response,req);
//    }

    /**
     * 注销登录
     */
    @ApiOperation(value = "注销登录",notes = "注销登录")
    @GetMapping(value = "/logout")
    public RespEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        return registerService.logout(request,response);
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码",notes = "用户修改密码")
    @PostMapping(value = "/changePassword")
    public RespEntity<?> changePassword(@RequestBody @Validated ChangePasswordReq req){
        return registerService.changePassword(req);
    }

    /**
     * 忘记密码
     */
    @ApiOperation(value = "忘记密码",notes = "用户找回密码")
    @PostMapping(value = "/retrievePassword")
    public RespEntity<?> retrievePassword(@RequestBody @Validated RetrievePasswordReq req){
        return registerService.retrievePassword(req);
    }


    @PostMapping("login")
    @ApiOperation(value = "用户登录",notes = "账号密码登录")
    public RespEntity login(UserLoginReq user){
        CommonUtil.hasAllRequired(JSON.parseObject(JSON.toJSONString(user)),"mobilePhone,password,type");
        RespEntity result = null;
        try {
            result=registerService.login(user);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return new RespEntity("密码错误",e.getMessage());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return new RespEntity("账号不存在",e.getMessage());
        } catch (DisabledAccountException e) {
            e.printStackTrace();
            return new RespEntity("3007",e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new RespEntity("服务器忙,稍后再试!!!!",e.getMessage());
        }
        return result;
    }

}
