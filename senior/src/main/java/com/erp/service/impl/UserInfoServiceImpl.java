package com.erp.service.impl;

import com.erp.dao.InformationMapper;
import com.erp.entity.Information;
import com.erp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    @Qualifier("informationMapper")
    private InformationMapper informationMapper;

    @Override
    public List<Information> findUserDetailInfo_GYL(Map<String, Object> paramsMap) {
        return informationMapper.findUserDetailInfo_GLY(paramsMap);
    }

    @Override
    public List<Information> findUserDetailInfo_BMYG(Map<String, Object> paramsMap) {
        return informationMapper.findUserDetailInfo_BMYG(paramsMap);
    }

    @Override
    public List<Information> findUserDetailInfo_GYS(Map<String, Object> paramsMap) {
        return informationMapper.findUserDetailInfo_GLY(paramsMap);
    }

    @Override
    public List<Information> findUserDetailInfo_KH(Map<String, Object> paramsMap) {
        return informationMapper.findUserDetailInfo_KH(paramsMap);
    }
}
