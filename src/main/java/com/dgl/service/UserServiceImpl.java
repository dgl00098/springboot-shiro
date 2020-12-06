package com.dgl.service;

import com.dgl.common.Constants;
import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.common.utils.RedisUtil;
import com.dgl.common.utils.ShiroMd5Util;
import com.dgl.common.utils.ShiroUtils;
import com.dgl.dao.UserRepository;
import com.dgl.smodel.entity.User;
import com.dgl.smodel.qo.ChangePasswordReq;
import com.dgl.smodel.qo.RetrievePasswordReq;
import com.dgl.smodel.qo.UserLoginReq;
import com.dgl.smodel.qo.UserRegisterReq;
import com.dgl.smodel.vo.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public RespEntity<?> userRegister(UserRegisterReq req) {
        //判断前后密码是否一致
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new CustomException(EnumErrorMsg.PWD_NOT_EQUALS);
        }
        User mobile = userRepo.findByMobile(req.getMobile());
        if (mobile!=null){
            throw new CustomException(EnumErrorMsg.TEL_HAS_EXIST);
        }
        //获取随机盐
        String salt = ShiroMd5Util.getSalt(5);
        //对密码进行md5+salt+hash散列加密
        String sysMd5 = ShiroMd5Util.SysMd5(req.getPassword(),salt);
        User user = new User(req.getUserName(),sysMd5, req.getMobile(),0,salt);
        User save = userRepo.save(user);
        return new RespEntity(save);
    }

    /**
     * 用户登录
     */
    @Override
    public RespEntity<?> userLogin(UserLoginReq req) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(req.getMobilePhone(), req.getPassword());
        subject.login(token);
        String sessionId= subject.getSession().getId().toString();
        //账号重复登陆验证
        checkRepeatLogin(req,sessionId);
        User userInfo = (User) ShiroUtils.getSessionAttribute(Constants.SESSION_USER_INFO);
        map.put("token", sessionId);
        map.put("userId", userInfo.getId());
        //设置当前用户的登录终端
        ShiroUtils.setSessionAttribute(Constants.SESSION_LOGIN_TYPE, req.getLoginType());
        return new RespEntity(map);
    }

    /**
     * 账号重复登陆验证
     * @param req
     * @param sessionId
     */
    private void checkRepeatLogin(UserLoginReq req,String sessionId){
        //判断用户是否已经登录
        Object preSessionId = redisUtil.hget(Constants.REDIS_ON_LOGIN_USER,req.getLoginType()+"_"+ShiroUtils.getUserId());
        if (preSessionId!=null){
            //将原session剔除
            redisUtil.del(Constants.REDIS_SHIRO_SESSION +preSessionId);
            log.info("原账号已经被挤掉了=========================================");
        }
        //将登录用户存入redis
        Map<String, Object> sessionMap=new HashMap<>();
        sessionMap.put(req.getLoginType()+"_"+ShiroUtils.getUserId(),sessionId);
        redisUtil.hmset(Constants.REDIS_ON_LOGIN_USER,sessionMap);
    }


    /**
     * 找回密码
     *
     * @param req
     * @return
     */
    @Override
    public RespEntity<?> retrievePassword(RetrievePasswordReq req) {
        //判断输入的账号是否存在
        User user = userRepo.findByMobile(req.getUserAccount());
        if (user == null) {
            throw new CustomException(EnumErrorMsg.USER_ACCOUNT_NOT_EXIST.getCode(), EnumErrorMsg.USER_ACCOUNT_NOT_EXIST.getMsg());
        }
        //根据用户账号和手机号查找用户
        User user1 = userRepo.findByMobile( req.getUserTel());
        if (user1 == null) {
            throw new CustomException(EnumErrorMsg.USER_TEL_NOT_EXIST.getCode(), EnumErrorMsg.USER_TEL_NOT_EXIST.getMsg());
        }
        //更新密码
        userRepo.updatePassword(DigestUtils.md5DigestAsHex(req.getUserPwd().getBytes()), req.getUserId());
        return new RespEntity<>();
    }

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @Override
    public RespEntity<?> changePassword(ChangePasswordReq req) {
        //判断旧密码是否正确
        User user = userRepo.findByMobile(req.getUserAccount());
        if (user == null) {
            throw new CustomException(EnumErrorMsg.USER_PWD_ERROR.getCode(), EnumErrorMsg.USER_PWD_ERROR.getMsg());
        }
        //判断新旧密码是否一致
        if (req.getOldPwd().equals(req.getNewPwd())) {
            throw new CustomException(EnumErrorMsg.OLD_AND_NEW_PWD_EQUALS.getCode(), EnumErrorMsg.OLD_AND_NEW_PWD_EQUALS.getMsg());
        } else if (req.getNewPwd().equals(req.getConfirmNewPwd())) {
            throw new CustomException(EnumErrorMsg.PWD_NOT_EQUALS.getCode(), EnumErrorMsg.PWD_NOT_EQUALS.getMsg());
        } else {
            userRepo.updatePassword(DigestUtils.md5DigestAsHex(req.getConfirmNewPwd().getBytes()), req.getUserId());
        }
        return new RespEntity<>();
    }

    /**
     * 注销登录
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public RespEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        return new RespEntity<>();
    }

    @Override
    public User findByMobile(String principal) {
        User user = userRepo.findByMobile(principal);
        return user;
    }

}
