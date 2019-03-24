package com.erp.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.common.PageUtil;
import com.erp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/WeChat")
public class WeChatController {

    @Autowired
    @Qualifier("weChatService")
    private WeChatService weChatService;

    @Autowired
    private PurchaseItemListService purchaseItemListService;

    @Autowired
    private SupplierAffordService supplierAffordService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    @Qualifier("orderService")
    private OrderService orderService;

    /**
     * PurchaseII
     */
    @RequestMapping("/getPurchaseList")
    @ResponseBody
    public Map<String,Object> getPurchaseList(@RequestParam Map<String,String> map){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(map.get("current")));
        page.setPageSize(Integer.parseInt(map.get("pageSize")));
        Page<Map<String,Object>> resultPage = weChatService.getPurchaseList(page.getPage(),map);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/getPurchaseItemList")
    @ResponseBody
    public Map<String,Object> getPurchaseItemList(String purchaseItemId){
        Map<String,Object> map = purchaseItemListService.getPurchaseItemList(purchaseItemId);
        return map;
    }

    @RequestMapping("/getSupplierAfford")
    @ResponseBody
    public Map<String,Object> getSupplierAfford(String purchaseItemId){
        Map<String,Object> map = supplierAffordService.getSupplierAfford(purchaseItemId);
        return map;
    }

    @RequestMapping("/getPurchaseStatistics")
    @ResponseBody
    public Map<String,Object> getPurchaseStatistics(){
        List<Map> list = weChatService.purchaseStatistics();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/getPurchaseListByState")
    @ResponseBody
    public Map<String,Object> getPurchaseListByState(@RequestParam Map<String,String> map){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(map.get("current")));
        page.setPageSize(Integer.parseInt(map.get("pageSize")));
        Page<Map<String,Object>> resultPage = weChatService.getPurchaseListByState(page.getPage(),map);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/getSupplierStatistics")
    @ResponseBody
    public Map<String,Object> getSupplierStatistics(){
        List<Map> list = weChatService.supplierStatistics();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/getSupplierListByState")
    @ResponseBody
    public Map<String,Object> getSupplierListByState(@RequestParam Map<String,String> map){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(map.get("current")));
        page.setPageSize(Integer.parseInt(map.get("pageSize")));
        Page<Map<String,Object>> resultPage = weChatService.getSupplierListByState(page.getPage(),map);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    /**
     * Class Member
     */
    @RequestMapping("/getMembers")
    @ResponseBody
    public Map<String,Object> getMembers(String className){
        List<Map> list = weChatService.getMembers(className);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/getProductDetail")
    @ResponseBody
    public Map<String,Object> getProductDetail(String productId){
        List<Map> list = weChatService.getProductDetail(productId);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/getCompanyOrderStatistics")
    @ResponseBody
    public Map<String,Object> getCompanyOrderStatistics(){
        return weChatService.companyOrderStatistics();
    }

    @RequestMapping("/reduceCartNum")
    @ResponseBody
    public Map<String,Object> reduceCartNum(@RequestParam Map<String,String> map){
        return weChatService.reduceCartNum(map);
    }

    @RequestMapping("/getOrderStatistics")
    @ResponseBody
    public Map<String,Object> getOrderStatistics(){
        List<Map> list = weChatService.orderStatistics();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/getOrderDetailByOrderId")
    @ResponseBody
    public Map<String,Object> getOrderDetailByOrderId(String orderId){
        return orderService.getOrderDetailByOrderId(orderId);
    }

    @RequestMapping("/getUserStatistics")
    @ResponseBody
    public Map<String,Object> getUserStatistics(){
        List<Map> list = weChatService.userStatistics();
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @RequestMapping("/warehouseI")
    @ResponseBody
    public Map<String,Object> warehouseI(@RequestParam Map<String,String> map){
        return weChatService.warehouseI(map);
    }

    @RequestMapping("/warehouseIs")
    @ResponseBody
    public Map<String,Object> warehouseIs(@RequestParam Map<String,String> map){
        return weChatService.warehouseIs(map);
    }
}
