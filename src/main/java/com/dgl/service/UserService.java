package com.dgl.service;

import com.dgl.model.entity.User;
import com.dgl.model.dto.ChangePasswordReq;
import com.dgl.model.dto.RetrievePasswordReq;
import com.dgl.model.dto.UserLoginReq;
import com.dgl.model.dto.UserRegisterReq;
import com.dgl.model.vo.RespEntity;

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
