package com.erp.service;

import com.erp.entity.Information;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
    List<Information> findUserDetailInfo_GYL(Map<String, Object> paramsMap);

    List<Information> findUserDetailInfo_BMYG(Map<String, Object> paramsMap);

    List<Information> findUserDetailInfo_GYS(Map<String, Object> paramsMap);

    List<Information> findUserDetailInfo_KH(Map<String, Object> paramsMap);
}
