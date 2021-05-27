package com.dgl.common;

/**
 * Created by captain on 2017/6/6.
 */
public class Constants {

    //定时任务cron参数:每天上午八点执行一次
    public static final String EverydayAM8 = "0 0 8 * * ? ";

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    //登录终端
    public static final String SESSION_LOGIN_TYPE ="login_type";
    //redis中用户信息
    public static final String REDIS_SHIRO_SESSION ="shiro:session:";
    //redis中用户权限信息
    public static final String REDIS_SHIRO_AUTH ="auth";

    //在线用户
    public static final String REDIS_ON_LOGIN_USER ="onLoginUser";
    //记录该用户登录的历史足迹
    public final static String BITMAP_LOGIN_USER_OFFSET = "bitmap:loginUserOffSet:";
    //记录当前所有登录的用户
    public final static String BITMAP_LOGIN_USER_DATE = "bitmap:loginUserDate:";


}
