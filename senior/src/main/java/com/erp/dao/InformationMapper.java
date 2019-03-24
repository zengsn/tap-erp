package com.erp.dao;

import com.erp.entity.Information;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("informationMapper")
public interface InformationMapper {
    List<Information> findUserDetailInfo_GLY(Map<String, Object> paramsMap);
    List<Information> findUserDetailInfo_BMYG(Map<String, Object> paramsMap);
    List<Information> findUserDetailInfo_KH(Map<String, Object> paramsMap);

    Map<String,String> findUserByUserName(String userName);
    List<Map<String,String>> findUserRoleCodeByUserName(String userName);
    List<Map<String,String>> findUserRoleNameByUserName(String userName);
}
