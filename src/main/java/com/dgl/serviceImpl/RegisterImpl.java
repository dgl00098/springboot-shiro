package com.dgl.serviceImpl;

import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.dao.UserRepository;
import com.dgl.service.RegisterService;
import com.dgl.smodel.domain.User;
import com.dgl.smodel.request.ChangePasswordReq;
import com.dgl.smodel.request.RetrievePasswordReq;
import com.dgl.smodel.request.UserLoginReq;
import com.dgl.smodel.request.UserRegisterReq;
import com.dgl.smodel.response.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RegisterImpl implements RegisterService {
    @Autowired
    UserRepository userRepo;


    /**
     * 用户注册
     */
    @Override
    public RespEntity<?> userRegister(UserRegisterReq req) {
        //判断前后密码是否一致
        if (!req.getUserPwd().equals(req.getConfirmUserPwd())) {
            throw new CustomException(EnumErrorMsg.PWD_NOT_EQUALS.getCode(), EnumErrorMsg.PWD_NOT_EQUALS.getMsg());
        }
        //判断该手机号是否已经注册
        User user = userRepo.findByUserTel(req.getUserTel());
        if (user != null) {
            throw new CustomException(EnumErrorMsg.TEL_HAS_EXIST.getCode(), EnumErrorMsg.TEL_HAS_EXIST.getMsg());
        }
        //判断该用户账号是否已经注册
        User user1 = userRepo.findByUserAccount(req.getUserAccount());
        if (user1 != null) {
            throw new CustomException(EnumErrorMsg.USER_ACCOUNT_HAS_EXIST.getCode(), EnumErrorMsg.USER_ACCOUNT_HAS_EXIST.getMsg());
        }
        //将用户密码进行加密
        String MD5Pwd = DigestUtils.md5DigestAsHex(req.getUserPwd().getBytes());
        User user2 = new User(req.getUserAccount(), MD5Pwd, req.getUserTel());
        userRepo.save(user2);
        return new RespEntity<>(EnumErrorMsg.REGISTER_SUCCESS);
    }

    /**
     * 用户登录
     */
    @Override
    public RespEntity<?> userLogin(HttpServletRequest request, HttpServletResponse response, UserLoginReq req) {

        User user = userRepo.findByUserAccountAndUserPwd(req.getUserAccount(), DigestUtils.md5DigestAsHex(req.getUserPwd().getBytes()));
        if (user == null) {
            return new RespEntity(EnumErrorMsg.ACCOUNT_OR_PWD_ERROR);
        }

        return new RespEntity<>(user);
    }

    /**
     * 找回密码
     * @param req
     * @return
     */
    @Override
    public RespEntity<?> retrievePassword(RetrievePasswordReq req) {
        //判断输入的账号是否存在
        User user = userRepo.findByUserAccount(req.getUserAccount());
        if (user==null){
            throw new CustomException(EnumErrorMsg.USER_ACCOUNT_NOT_EXIST.getCode(), EnumErrorMsg.USER_ACCOUNT_NOT_EXIST.getMsg());
        }
        //根据用户账号和手机号查找用户
        User user1 = userRepo.findByUserAccountAndUserTel(req.getUserAccount(),req.getUserTel());
        if (user1==null){
            throw new CustomException(EnumErrorMsg.USER_TEL_NOT_EXIST.getCode(), EnumErrorMsg.USER_TEL_NOT_EXIST.getMsg());
        }
        //更新密码
        userRepo.updatePassword(DigestUtils.md5DigestAsHex(req.getUserPwd().getBytes()),req.getUserId());
        return new RespEntity<>();
    }

    /**
     * 修改密码
     * @param req
     * @return
     */
    @Override
    public RespEntity<?> changePassword(ChangePasswordReq req) {
        //判断旧密码是否正确
        User user = userRepo.findByUserAccountAndUserPwd(req.getUserAccount(), DigestUtils.md5DigestAsHex(req.getOldPwd().getBytes()));
        if (user==null){
            throw new CustomException(EnumErrorMsg.USER_PWD_ERROR.getCode(), EnumErrorMsg.USER_PWD_ERROR.getMsg());
        }
        //判断新旧密码是否一致
        if (req.getOldPwd().equals(req.getNewPwd())){
            throw new CustomException(EnumErrorMsg.OLD_AND_NEW_PWD_EQUALS.getCode(), EnumErrorMsg.OLD_AND_NEW_PWD_EQUALS.getMsg());
        }else if(req.getNewPwd().equals(req.getConfirmNewPwd())){
            throw new CustomException(EnumErrorMsg.PWD_NOT_EQUALS.getCode(), EnumErrorMsg.PWD_NOT_EQUALS.getMsg());
        }else{
            userRepo.updatePassword(DigestUtils.md5DigestAsHex(req.getConfirmNewPwd().getBytes()),req.getUserId());
        }
        return new RespEntity<>();
    }

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     */
    @Override
    public RespEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        return new RespEntity<>();
    }

}
