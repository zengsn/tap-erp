package com.erp.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.common.PageUtil;
import com.erp.entity.Information;
import com.erp.entity.SupplierAfford;
import com.erp.plugin.LoginPlugin;
import com.erp.service.SupplierAffordService;
import com.erp.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequestMapping("/supplier")
@Controller
public class SupplierController {

    @Autowired
    @Qualifier("supplierAffordService")
    private SupplierAffordService supplierAffordService;

    @Autowired
    @Qualifier("supplierService")
    private SupplierService supplierService;

    @RequestMapping("/purchaseItemPage")
    public String turnToPurchaseItemPage(){
        return "/supplier/purchaseItemPage";
    }

    @RequestMapping("/supplierAffordPage")
    public String turnToSupplierAffordPage(){
        return "/supplier/supplierAffordPage";
    }

    @RequestMapping("/turnToEditSupplyContent")
    public String turnToEditSupplyContent(){
        return "/supplier/editSupplyContent";
    }

    @RequestMapping("/insertSupplierAfford")
    @ResponseBody
    public Map<String,Object> insertPurchaseItem(SupplierAfford supplierAfford){
        Information information = LoginPlugin.getLoginUserModel();
        return supplierAffordService.insertSupplierAfford(supplierAfford,information);
    }

    @RequestMapping("/getSupplierPurchaseItemList")
    @ResponseBody
    public Map<String,Object> getSupplierPurchaseItemList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));

        Page<Map<String,Object>> resultPage = supplierService.getPurchaseItemList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/getOwnSupplierAffordList")
    @ResponseBody
    public Map<String,Object> getOwnSupplierAffordList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));
        Page<Map<String,Object>> resultPage = supplierAffordService.getOwnSupplierAffordList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/turnToViewSupplierAfford")
    public ModelAndView turnToViewSupplierAfford(ModelAndView mv, String purchaseItemId){
        Map<String,Object> map = supplierAffordService.getSupplierAfford(purchaseItemId);
        mv.addObject("supplierAfford",map.get("data"));
        mv.setViewName("/purchase/viewSupplierAfford");
        return mv;
    }

    @RequestMapping("/viewSupplierAfford")
    @ResponseBody
    public Map<String,Object> turnToViewSupplierAfford(String purchaseItemId){
        return supplierAffordService.getSupplierAfford(purchaseItemId);
    }
}
