package com.erp.controller;

import com.erp.common.FrontUtil;
import com.erp.entity.ProductType;
import com.erp.service.CommonAjaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/commonAjax")
public class CommonAjaxController {

    @Autowired
    @Qualifier("commonAjaxService")
    private CommonAjaxService commonAjaxService;

    @RequestMapping("/queryRoleName")
    @ResponseBody
    public Map<String,Object> queryRoleName(){
        List<Map> list = commonAjaxService.queryRoleName();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/queryDepartmentName")
    @ResponseBody
    public Map<String,Object> queryDepartmentName(){
        List<Map> list = commonAjaxService.queryDepartmentName();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/queryCompanyName")
    @ResponseBody
    public Map<String,Object> queryCompanyName(){
        List<Map> list = commonAjaxService.queryCompanyName();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/querySupplierName")
    @ResponseBody
    public Map<String,Object> querySupplierName(){
        List<Map> list = commonAjaxService.querySupplierName();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/queryPurchaseType")
    @ResponseBody
    public Map<String,Object> queryPurchaseType(String parentTypeId){
        List<Map> list = commonAjaxService.queryPurchaseType(parentTypeId);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/queryProductTypeName")
    @ResponseBody
    public Map<String,Object> queryProductTypeName(String parentTypeId){
        List<Map> list = commonAjaxService.queryProductTypeName(parentTypeId);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/queryParentTypeName")
    @ResponseBody
    public Map<String,Object> queryParentTypeName(String companyName){
        List<Map> list = commonAjaxService.queryParentTypeName(companyName);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/queryAllParentTypeName")
    @ResponseBody
    public Map<String,Object> queryParentTypeName(){
        List<Map> list = commonAjaxService.queryAllParentTypeName();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/getSimpleData")
    @ResponseBody
    public List<Map> getSimpleData(){
        List<Map> list = commonAjaxService.getSimpleData();
//        System.out.println(list.size());
//        for (int i=0;i<list.size();i++){
//            Map map = new HashMap();
//            map.put("id",list.get(i).get("productCode"));
//            map.put("pId",list.get(i).get("productPriorCode"));
//            list.get(i).putAll(map);
//        }
        return list;
    }
}
