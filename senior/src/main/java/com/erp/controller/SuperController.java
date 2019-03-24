package com.erp.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.common.PageUtil;
import com.erp.entity.Company;
import com.erp.entity.Supplier;
import com.erp.entity.User;
import com.erp.service.CompanyService;
import com.erp.service.SupplierService;
import com.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/super")
public class SuperController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    @Autowired
    @Qualifier("supplierService")
    private SupplierService supplierService;

    @RequestMapping("/managerList")
    public String addCompany(){
        return "super/managerList";
    }

    @RequestMapping("/addSystemUser")
    public String addSystemUser(){
        return "/super/addSystemUser";
    }

    @RequestMapping("/systemUserList")
    public String systemUserList(){
        return "/super/systemUserList";
    }

    @RequestMapping("/detailSystemUserList")
    public String detailSystemUserList(){
        return "/super/detailSystemUserList";
    }

    @RequestMapping("/businessManagement")
    public String businessManagement(){
        return "/super/businessManagement";
    }

    @RequestMapping("/insertSystemUser")
    @ResponseBody
    public Map<String,Object> insertSystemUser(User user){
        Map<String,Object> map = userService.insertSystemUser(user);
        return map;
    }

    @RequestMapping("/getCompanyList")
    @ResponseBody
    public Map<String,Object> getCompanyList(@RequestParam Map<String,String> params){
        PageUtil<Map<String,Object>> page = new PageUtil<Map<String, Object>>();
        page.setCurrent(Integer.parseInt(params.get("current")));
        page.setPageSize(Integer.parseInt(params.get("pageSize")));

        Page<Map<String,Object>> resultPage = companyService.getCompanyList(page.getPage(),params);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/turnToAddCompany")
    public String turnToAddCompany(){
        return "super/managerCompany";
    }

    @RequestMapping("/addCompany")
    @ResponseBody
    public Map<String,Object> addCompany(Company company){
        return companyService.addCompany(company);
    }

    @RequestMapping("/deleteCompanyById")
    @ResponseBody
    public Map<String,Object> deleteCompanyById(Company company){
        return companyService.deleteCompanyById(company);
    }

    @RequestMapping("/getSupplierList")
    @ResponseBody
    public Map<String,Object> getSupplierList(@RequestParam Map<String,String> params){
        PageUtil<Map<String,Object>> page = new PageUtil<Map<String, Object>>();
        page.setCurrent(Integer.parseInt(params.get("current")));
        page.setPageSize(Integer.parseInt(params.get("pageSize")));

        Page<Map<String,Object>> resultPage = supplierService.getSupplierList(page.getPage(),params);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/turnToAddSupplier")
    public String turnToAddSupplier(){
        return "super/managerSupplier";
    }

    @RequestMapping("/addSupplier")
    @ResponseBody
    public Map<String,Object> addSupplier(Supplier supplier){
        return supplierService.addSupplier(supplier);
    }

    @RequestMapping("/getSystemUserList")
    @ResponseBody
    public Map<String,Object> getSystemUserList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<Map<String, Object>>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));

        Page<Map<String,Object>> resultPage = userService.getSystemUserList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/updateSystemUserByuserId")
    @ResponseBody
    public Map<String,Object> updateSystemUserByuserId(User user){
        Map<String,Object> map = userService.updateSystemUserByuserId(user);
        return map;
    }
}
