package com.erp.plugin;

import com.erp.service.InformationService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SysBackRealm extends AuthorizingRealm{

    @Autowired
    @Qualifier("informationService")
    private InformationService informationService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) getAvailablePrincipal(principalCollection);
        Set<String> roleCode = informationService.getRoleCodeByUserName(userName);
        Set<String> roleName = informationService.getRoleNameByUserName(userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleCode);
        info.setStringPermissions(roleName);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = null;
        String userName = upToken.getUsername();
        Map<String,String> userInfo = informationService.getUserInfoByUserName(userName);
        info = new SimpleAuthenticationInfo(userName,userInfo.get("password"),getName());
        return info;
    }
}
