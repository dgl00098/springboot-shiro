package com.dgl.serviceImpl;

import com.dgl.common.Constants;
import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.common.Enum.EnumUserType;
import com.dgl.common.utils.ShiroMd5Util;
import com.dgl.common.utils.ShiroUtils;
import com.dgl.dao.UserRepository;
import com.dgl.service.UserService;
import com.dgl.smodel.domain.User;
import com.dgl.smodel.request.ChangePasswordReq;
import com.dgl.smodel.request.RetrievePasswordReq;
import com.dgl.smodel.request.UserLoginReq;
import com.dgl.smodel.request.UserRegisterReq;
import com.dgl.smodel.response.RespEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;

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
        User user = new User(sysMd5, req.getMobile(),0,salt);
        User save = userRepo.save(user);
        return new RespEntity(save);
    }

    /**
     * 用户登录
     */
    @Override
    public RespEntity<?> userLogin(HttpServletRequest request, HttpServletResponse response, UserLoginReq req) {

        User user = userRepo.findByMobile(req.getMobilePhone());
        if (user == null) {
            return new RespEntity(EnumErrorMsg.ACCOUNT_OR_PWD_ERROR);
        }

        return new RespEntity<>(user);
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
    public RespEntity login(UserLoginReq userVO) {

        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userVO.getMobilePhone(), userVO.getPassword());
        subject.login(token);
        String sessionId = subject.getSession().getId().toString();
        //账号重复登陆验证
        //saveLoginUser(userVO,sessionId);
        User userInfo = (User) ShiroUtils.getSessionAttribute(Constants.SESSION_USER_INFO);
        map.put("token", sessionId);
        map.put("userId", userInfo.getId());
        //判断在哪个app端登录
        ShiroUtils.setSessionAttribute(Constants.SYS_TYPE, userVO.getType());
        if (EnumUserType.A1.getCode().equalsIgnoreCase(userVO.getType()) || EnumUserType.P1.getCode().equalsIgnoreCase(userVO.getType())) {
            Map<String, String> param = new HashMap<>();
            param.put("token", sessionId);
            param.put("userId", userInfo.getId().toString());
            //String res= HttpClientUtil.doPost(url,param);
        }
        return new RespEntity(map);

    }

}
