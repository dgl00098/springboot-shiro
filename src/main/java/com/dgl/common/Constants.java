package com.dgl.common;

/**
 * Created by captain on 2017/6/6.
 */
public class Constants {

    //session
    public static final int DEFAULT_SESSION_MAX_AGE = 7 * 24 * 60 * 60;
    public static final String TOKEN_USER = "token_user";
    public static final String COOKIE_KEY = "user_cookie";
    public static final String COMMON_FILE = "common_file";
    public static String UPLOAD_FILE_PATH = "/usr/local/upload/";
    public static String SAVE_PATH = "/upload/";
    //外网地址映射
    public static String ACCESS_FILE_PATH = "http://duguanglei.free.idcfengye.com";

    //定时任务cron参数:每天上午八点执行一次
    public static final String EverydayAM8 = "0 0 8 * * ? ";
    //定时任务cron参数:每分钟执行一次
    public static final String EveryMinuteOfDay = "0 0/1 * * * ? ";
    //定时任务cron参数:每月1号0点执行
    public static final String FirstDayOfEveryMonth = "0 0 0 1 * ? ";
    //定时任务cron参数:每日早晚八点半各执行一次
    public static final String EverydayTwice = "0 30 8,20 * * ? ";

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    //登录终端
    public static final String LOGIN_TYPE="login_type";
    //redis中用户信息
    public static final String SHIRO_SESSION="shiro:session:";

    //登陆用户信息
    public static final String LOGIN_USER="loginUser";

    //在线用户
    public static final String ON_LOGIN_USER="onLoginUser";


}
