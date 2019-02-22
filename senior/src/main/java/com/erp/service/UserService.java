package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends ISuperService<User>{
    Map<String,Object> insertSystemUser(User user);

    Page<Map<String,Object>> getSystemUserList(Page<Map<String,Object>> page, Map<String,String> paramsMap);

    Map<String,Object> updateSystemUserByuserId(User user);

    Map<String,Object> getUserInfoWX(String userName);

}
