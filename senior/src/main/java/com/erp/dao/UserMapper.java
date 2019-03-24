package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userMapper")
public interface UserMapper extends AutoMapper<User>{
    int insertSystemUser(User user);
    List<Map<String,Object>> getSystemUserList(Pagination page, Map<String,String> paramsMap);
    int updateSystemUserByuserId(User user);
    List<User> getUserInfoWX(String userName);
    String getUserMail(String userId);
}
