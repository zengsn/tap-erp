package com.erp.service;


import java.util.Map;
import java.util.Set;

public interface InformationService {

    public Set<String> getRoleCodeByUserName(String userName);

    public Set<String> getRoleNameByUserName(String userName);

    public Map<String,String> getUserInfoByUserName(String userName);
}
