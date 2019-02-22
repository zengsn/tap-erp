package com.erp.service.impl;

import com.erp.common.AESUtil;
import com.erp.entity.Information;
import com.erp.plugin.LoginPlugin;
import com.erp.service.InformationService;
import com.erp.service.LoginService;
import com.erp.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    @Qualifier("informationService")
    private InformationService informationService;

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("redisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public void login(Map<String, Object> paramsMap) {
        String userName = paramsMap.get("userName") != null ? paramsMap.get("userName").toString() : "";
        //String password = paramsMap.get("password") != null ? AESUtil.aesDecryptRedis((String) paramsMap.get("password"),redisTemplate.opsForValue().get("AES")) : "";
        String password = paramsMap.get("password") != null ? AESUtil.aesEncrypt((String) paramsMap.get("password")) : "";
        UsernamePasswordToken upToken = new UsernamePasswordToken(userName,password);
        upToken.setRememberMe(true);

        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userName",userName);
        List<Information> resultList = null;

        if(subject.hasRole("GLY")){
            resultList = userInfoService.findUserDetailInfo_GYL(map);
        }else if(subject.hasRole("BMYG")){
            resultList = userInfoService.findUserDetailInfo_BMYG(map);
        }else if(subject.hasRole("GYS")){
            resultList = userInfoService.findUserDetailInfo_GYS(map);
        }else if(subject.hasRole("KH")){
            resultList = userInfoService.findUserDetailInfo_KH(map);
        }

        if(resultList != null && resultList.size() > 0){

            Information information = resultList.get(0);
            if(subject.hasRole("GLY")){
                information.setIsAdmin(true);
            }else if (subject.hasRole("BMYG") && information.getDepartmentName().equals("仓库部门")){
                information.setIsWarehouseEmployee(true);
            }else if (subject.hasRole("BMYG") && information.getDepartmentName().equals("采购部门")){
                information.setIsPurchaseEmployee(true);
            }else if (subject.hasRole("BMYG") && information.getDepartmentName().equals("审批部门")){
                information.setIsApproveEmployee(true);
            }else if (subject.hasRole("BMYG") && information.getDepartmentName().equals("销售部门")){
                information.setIsSellEmployee(true);
            }else if (subject.hasRole("GYS")){
                information.setIsSupplier(true);
            }else if(subject.hasRole("KH")){
                information.setIsCustomer(true);
            }
            Set<String> roleCode = informationService.getRoleCodeByUserName(userName);
            information.setRoleCode(roleCode);
            subject.getSession().removeAttribute(LoginPlugin.LOGIN_USER);
            subject.getSession().setAttribute(LoginPlugin.LOGIN_USER,information);

        }
    }
}
