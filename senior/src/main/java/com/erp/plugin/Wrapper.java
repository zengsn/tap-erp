package com.erp.plugin;

import com.erp.dao.UserMapper;

public class Wrapper {
    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
