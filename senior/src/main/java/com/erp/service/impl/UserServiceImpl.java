package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.AESUtil;
import com.erp.common.FrontUtil;
import com.erp.dao.UserMapper;
import com.erp.entity.User;
import com.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl extends SuperServiceImpl<UserMapper,User> implements UserService{

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String,Object> insertSystemUser(User user){
        List<User> list = userMapper.getUserInfoWX(user.getUserName());
        if(list.size() > 0){
            return FrontUtil.returnToFront(false,"","用户名重复！");
        }
        user.setUserId(UUID.randomUUID().toString());
        user.setEnables("1");
        user.setPassword(AESUtil.aesEncryptRedis(user.getPassword(),redisTemplate.opsForValue().get("AES")));
        try {
            userMapper.insertSystemUser(user);
            return FrontUtil.returnToFront(true,"","新增系统用户成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","新增系统用户失败！");
        }

    }

    @Override
    public Page<Map<String, Object>> getSystemUserList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        page.setRecords(userMapper.getSystemUserList(page,paramsMap));
        return page;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String,Object> updateSystemUserByuserId(User user) {
        try {
            userMapper.updateSystemUserByuserId(user);
            return FrontUtil.returnToFront(true,"","删除系统用户成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","删除系统用户失败！");
        }
    }

    @Override
    public Map<String, Object> getUserInfoWX(String userName) {
        List<User> list = null;
        try {
            list = userMapper.getUserInfoWX(userName);
            return FrontUtil.returnToFront(true,list,"成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","失败！");
        }
    }
}
