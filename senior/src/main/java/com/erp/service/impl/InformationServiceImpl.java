package com.erp.service.impl;

import com.erp.dao.InformationMapper;
import com.erp.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("informationService")
public class InformationServiceImpl implements InformationService{

    @Autowired
    @Qualifier("informationMapper")
    private InformationMapper informationMapper;

    @Override
    public Set<String> getRoleCodeByUserName(String userName) {
        List<Map<String,String>> resultList = informationMapper.findUserRoleCodeByUserName(userName);
        Iterator<Map<String,String>> it = resultList.iterator();
        Map<String,String> map = null;
        Set<String> set = new HashSet<String>();
        while(it.hasNext()){
            map = it.next();
            set.add(map.get("roleCode"));
        }
        return set;
    }

    @Override
    public Set<String> getRoleNameByUserName(String userName) {
        List<Map<String,String>> resultList = informationMapper.findUserRoleNameByUserName(userName);
        Iterator<Map<String,String>> it = resultList.iterator();
        Map<String,String> map = null;
        Set<String> set = new HashSet<String>();
        while (it.hasNext()){
            map = it.next();
            set.add(map.get("roleName"));
        }
        return set;
    }

    @Override
    public Map<String, String> getUserInfoByUserName(String userName) {
        return informationMapper.findUserByUserName(userName);
    }
}
