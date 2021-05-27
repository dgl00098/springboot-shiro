package com.dgl.service;

import com.dgl.smodel.entity.User;
import com.dgl.smodel.qo.ChangePasswordReq;
import com.dgl.smodel.qo.RetrievePasswordReq;
import com.dgl.smodel.qo.UserLoginReq;
import com.dgl.smodel.qo.UserRegisterReq;
import com.dgl.smodel.vo.RespEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 注册管理
 */
public interface UserService {

    /**
     * 用户注册
     * @param req
     * @return
     */
    User userRegister(UserRegisterReq req);

    /**
     * 用户登录
     * @param req
     * @return
     */
    Map<String, Object> userLogin(UserLoginReq req);

    /**
     * 找回密码
     * @param req
     * @return
     */
    Integer retrievePassword(RetrievePasswordReq req);

    /**
     * 修改密码
     * @param req
     * @return
     */
    Integer changePassword(ChangePasswordReq req);

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     */
    RespEntity<?> logout(HttpServletRequest request, HttpServletResponse response);

    User findByMobile(String principal);
}
