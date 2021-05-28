package com.dgl.service;

import com.dgl.common.Constants;
import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.common.Enum.EnumYesOrNo;
import com.dgl.common.utils.DGLDateUtils;
import com.dgl.common.utils.RedisUtil;
import com.dgl.common.utils.ShiroMd5Util;
import com.dgl.common.utils.ShiroUtils;
import com.dgl.dao.UserRepository;
import com.dgl.model.entity.User;
import com.dgl.model.dto.ChangePasswordReq;
import com.dgl.model.dto.RetrievePasswordReq;
import com.dgl.model.dto.UserLoginReq;
import com.dgl.model.dto.UserRegisterReq;
import com.dgl.model.vo.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public User userRegister(UserRegisterReq req) {
        Optional<User> byMobile = userRepo.findByMobile(req.getMobile());
        if (byMobile.isPresent()){
            throw new CustomException(EnumErrorMsg.TEL_HAS_EXIST);
        }
        //对密码进行md5+salt+hash散列加密
        String sysMd5 = ShiroMd5Util.SysMd5(req.getPassword(), req.getPassword());
        User user = new User(req.getUserName(), sysMd5, req.getMobile(), req.getUserType(), req.getPassword());
        User save = userRepo.save(user);
        return save;
    }

    /**
     * 用户登录
     */
    @Override
    public Map<String, Object> userLogin(UserLoginReq req) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(req.getMobilePhone(), req.getPassword());
        subject.login(token);
        String sessionId = subject.getSession().getId().toString();
        //账号重复登陆验证
        checkRepeatLogin(req, sessionId);
        User userInfo = (User) ShiroUtils.getSessionAttribute(Constants.SESSION_USER_INFO);
        map.put("token", sessionId);
        map.put("userId", userInfo.getId());
        map.put("userName", userInfo.getUserName());
        //设置当前用户的登录终端
        ShiroUtils.setSessionAttribute(Constants.SESSION_LOGIN_TYPE, req.getLoginType());
        //更新用户登录的天数(记录该用户的登录足迹)
        redisUtil.bitMapSet(Constants.BITMAP_LOGIN_USER_OFFSET + ":" + userInfo.getId(), LocalDate.now().getDayOfYear(), true);
        //更新用户登录的日期
        String yyyyMMdd = DGLDateUtils.ldToyyyyMMdd(LocalDate.now(), DGLDateUtils.FORMAT_SHORT);
        redisUtil.bitMapSet(Constants.BITMAP_LOGIN_USER_DATE + yyyyMMdd + ":", userInfo.getId(), true);
        return map;
    }

    /**
     * 账号重复登陆验证
     *
     * @param req
     * @param sessionId
     */
    private void checkRepeatLogin(UserLoginReq req, String sessionId) {
        //判断用户是否已经登录
        Object preSessionId = redisUtil.hget(Constants.REDIS_ON_LOGIN_USER, req.getLoginType() + "_" + ShiroUtils.getUserId());
        if (preSessionId != null) {
            //将原session剔除
            redisUtil.del(Constants.REDIS_SHIRO_SESSION + preSessionId);
            log.info("原账号已经被挤掉了=========================================");
        }
        //将登录用户存入redis
        Map<String, Object> sessionMap = new HashMap<>();
        sessionMap.put(req.getLoginType() + "_" + ShiroUtils.getUserId(), sessionId);
        redisUtil.hmset(Constants.REDIS_ON_LOGIN_USER, sessionMap);
    }


    /**
     * 找回密码
     *
     * @param req
     * @return
     */
    @Override
    public Integer retrievePassword(RetrievePasswordReq req) {
        //判断输入的账号是否存在
        userRepo.findByMobile(req.getUserAccount()).orElseThrow(() -> new CustomException(EnumErrorMsg.USER_ACCOUNT_NOT_EXIST));
        //更新密码
        int updatePassword = userRepo.updatePassword(DigestUtils.md5DigestAsHex(req.getUserPwd().getBytes()), req.getUserId());
        return updatePassword;
    }

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @Override
    public Integer changePassword(ChangePasswordReq req) {
        //判断旧密码是否正确
        String sysMd5 = ShiroMd5Util.SysMd5(req.getOldPwd(), req.getOldPwd());
        userRepo.findByMobileAndPassword(req.getUserAccount(),sysMd5).orElseThrow(() -> new CustomException(EnumErrorMsg.USER_PWD_ERROR));
        //判断新旧密码是否一致
        if (req.getOldPwd().equals(req.getNewPwd())) {
            throw new CustomException(EnumErrorMsg.OLD_AND_NEW_PWD_EQUALS.getCode(), EnumErrorMsg.OLD_AND_NEW_PWD_EQUALS.getMsg());
        } else if (req.getNewPwd().equals(req.getConfirmNewPwd())) {
            throw new CustomException(EnumErrorMsg.PWD_NOT_EQUALS.getCode(), EnumErrorMsg.PWD_NOT_EQUALS.getMsg());
        } else {
            userRepo.updatePassword(DigestUtils.md5DigestAsHex(req.getConfirmNewPwd().getBytes()), req.getUserId());
        }
        return EnumYesOrNo.YES.getCode();
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
        ShiroUtils.getSubject().logout();
        return new RespEntity<>();
    }

    @Override
    public User findByMobile(String principal) {
        User user = userRepo.findByMobile(principal).orElseThrow(() -> new CustomException(EnumErrorMsg.USER_ACCOUNT_NOT_EXIST));
        return user;
    }

}
