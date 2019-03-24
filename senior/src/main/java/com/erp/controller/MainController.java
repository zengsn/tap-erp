package com.erp.controller;

import com.erp.common.FrontUtil;
import com.erp.common.JSONUtil;
import com.erp.entity.Information;
import com.erp.plugin.LoginPlugin;
import com.erp.plugin.MenuWebVo;
import com.erp.service.InformationService;
import com.erp.service.LoginService;
import com.erp.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    @Qualifier("loginService")
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/loginSystem")
    public String loginSystem(String userName, String password, Model model){
        Map<String,Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userName",userName);
        paramsMap.put("password",password);
        loginService.login(paramsMap);
        return "redirect:/main";
    }



    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response,Model model){
        SecurityUtils.getSubject().getSession().removeAttribute(LoginPlugin.LOGIN_USER);
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("rememberMe")){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return toLogin();
    }

    @RequestMapping("/main")
    public String main(){
        return "/main2";
    }

    @RequestMapping("/main/getMenuList.json")
    @ResponseBody
    public MenuWebVo getMenuList(){
        Information loginUser = LoginPlugin.getLoginUserModel();
        if(loginUser.getMenuItems() != null){
            MenuWebVo vo = new MenuWebVo();
            vo.setSuccess("true");
            vo.setMsg("获取成功！");
            vo.setData(loginUser.getMenuItems());
            return vo;
        }
        MenuWebVo menu = null;
        String menuStr = readMenuJsonFromFile();
        if(menuStr != null){
            menu = JSONUtil.json2pojo(menuStr,MenuWebVo.class);
            List<MenuWebVo.MenuItem> filterMenuList = filterMenuByLoginUser(menu);
            menu.setData(filterMenuList);
            loginUser.setMenuItems(filterMenuList);
            SecurityUtils.getSubject().getSession().removeAttribute(LoginPlugin.LOGIN_USER);
            SecurityUtils.getSubject().getSession().setAttribute(LoginPlugin.LOGIN_USER,loginUser);
        }
        return menu;
    }

    private String readMenuJsonFromFile(){
        try {
            String str = redisTemplate.opsForValue().get("start");
            str = str.replaceAll("|\r|\n|\t", "");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<MenuWebVo.MenuItem> filterMenuByLoginUser(MenuWebVo vo){
        List<MenuWebVo.MenuItem> menuList = vo.getData();
        List<MenuWebVo.MenuItem> filterMenuList = new ArrayList<MenuWebVo.MenuItem>();
        Information loginUser = LoginPlugin.getLoginUserModel();
        for(MenuWebVo.MenuItem m:menuList){
            boolean flag = true;
            if(m.getNeedERPRoles() != null){
                flag = false;
                for(String needRole : m.getNeedERPRoles()){
                    try {
                        flag = Boolean.parseBoolean(invokeGetMethod(loginUser,needRole).toString());
                        if(flag)break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        flag = false;
                    }
                }
            }
            if(flag){
                filterMenuList.add(m);
            }
        }
        return filterMenuList;
    }

    private Object invokeGetMethod(Object obj,String fieldName) throws Exception{
        @SuppressWarnings("rawtypes")
        Class cls = obj.getClass();
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        @SuppressWarnings("unchecked")
        Method getMethod = cls.getDeclaredMethod(getMethodName, new Class[] {});
        return getMethod.invoke(obj, null);
    }

    @RequestMapping("/WeChatLogin")
    public String WeChatLogin(String userName, String password, Model model, HttpServletResponse response, HttpSession session) throws Exception{
        Map<String,Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userName",userName);
        paramsMap.put("password",password);
        loginService.login(paramsMap);
        Information information = LoginPlugin.getLoginUserModel();
        System.out.println(information.getUserName());
        System.out.println("sessionId" + session.getId());
        return "redirect:/main/getMenu";
    }

    @RequestMapping("/main/getMenu")
    @ResponseBody
    public MenuWebVo getMenu(HttpServletResponse response,HttpSession session){
        Information loginUser = LoginPlugin.getLoginUserModel();
        response.setHeader("Set-Cookie",session.getId());
        if(loginUser.getMenuItems() != null){
            MenuWebVo vo = new MenuWebVo();
            vo.setSuccess("true");
            vo.setMsg("成功！");
            vo.setData(loginUser.getMenuItems());
            return vo;
        }
        MenuWebVo menu = null;
        String menuStr = readMenuJsonFromFile();
        if(menuStr != null){
            menu = JSONUtil.json2pojo(menuStr,MenuWebVo.class);
            List<MenuWebVo.MenuItem> filterMenuList = filterMenuByLoginUser(menu);
            menu.setData(filterMenuList);
            loginUser.setMenuItems(filterMenuList);
            SecurityUtils.getSubject().getSession().removeAttribute(LoginPlugin.LOGIN_USER);
            SecurityUtils.getSubject().getSession().setAttribute(LoginPlugin.LOGIN_USER,loginUser);
        }
        System.out.println(JSONUtil.obj2json(menu));
        return menu;
    }

    @RequestMapping("/getUserInfoWX")
    @ResponseBody
    public Map<String,Object> getUserInfoWX(String userName){
        return userService.getUserInfoWX(userName);
    }

}
