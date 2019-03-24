package com.erp.plugin;


import org.apache.shiro.SecurityUtils;

public class LoginPlugin {
    public static final String LOGIN_USER = "Login_User";

    public LoginPlugin() {
    }

    @SuppressWarnings("unchecked")
    public static<T> T getLoginUserModel(){
        return (T) SecurityUtils.getSubject().getSession().getAttribute(LOGIN_USER);
    }
}
