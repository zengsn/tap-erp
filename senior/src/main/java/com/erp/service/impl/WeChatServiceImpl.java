package com.erp.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.dao.WeChatMapper;
import com.erp.entity.Information;
import com.erp.plugin.LoginPlugin;
import com.erp.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("weChatService")
public class WeChatServiceImpl implements WeChatService{

    @Autowired
    @Qualifier("weChatMapper")
    private WeChatMapper weChatMapper;


    @Override
    public Page<Map<String, Object>> getPurchaseList(Page<Map<String, Object>> page, Map<String, String> map) {
        Information information = LoginPlugin.getLoginUserModel();
        String userId = information.getUserId();
        //map.put("userId",userId);
        map.put("companyName",information.getCompanyName());
        map.put("departmentName",information.getDepartmentName());
        page.setRecords(weChatMapper.getPurchaseList(page,map));
        return page;
    }

    @Override
    public List<Map> purchaseStatistics() {
        Information information = LoginPlugin.getLoginUserModel();
        String userId = information.getUserId();
        return weChatMapper.purchaseStatistics(userId);
    }

    @Override
    public Page<Map<String, Object>> getPurchaseListByState(Page<Map<String, Object>> page, Map<String, String> map) {
        Information information = LoginPlugin.getLoginUserModel();
        String userId = information.getUserId();
        map.put("userId",userId);
        page.setRecords(weChatMapper.getPurchaseListByState(page,map));
        return page;
    }

    @Override
    public List<Map> supplierStatistics() {
        Information information = LoginPlugin.getLoginUserModel();
        String userId = information.getUserId();
        return weChatMapper.supplierStatistics(userId);
    }

    @Override
    public Page<Map<String, Object>> getSupplierListByState(Page<Map<String, Object>> page, Map<String, String> map) {
        Information information = LoginPlugin.getLoginUserModel();
        String supplierBehalfId = information.getUserId();
        map.put("supplierBehalfId",supplierBehalfId);
        page.setRecords(weChatMapper.getSupplierListByState(page,map));
        return page;
    }

    @Override
    public List<Map> getMembers(String className) {
        Information information = LoginPlugin.getLoginUserModel();
        String userId = information.getUserId();
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("className",className);
        return weChatMapper.getMembers(map);
    }

    @Override
    public List<Map> getProductDetail(String productId) {
        return weChatMapper.getProductDetail(productId);
    }

    @Override
    public Map<String, Object> companyOrderStatistics() {
        Information information = LoginPlugin.getLoginUserModel();
        List<Map> list = weChatMapper.companyOrderStatistics(information.getCompanyName());
        if(list != null){
            return FrontUtil.returnToFront(true,list,"成功！");
        }else{
            return FrontUtil.returnToFront(false,"","失败！");
        }
    }

    @Override
    public Map<String, Object> reduceCartNum(Map<String,String> map) {
        String orderDetailId = map.get("orderDetailId");
        BigDecimal productNumber = new BigDecimal(map.get("productNumber"));//原有量
        BigDecimal unitPrice = new BigDecimal(map.get("unitPrice"));
        BigDecimal reduceNum = new BigDecimal(map.get("reduceNum"));//减少量

        productNumber = productNumber.subtract(reduceNum);
        BigDecimal totalPrice = productNumber.multiply(unitPrice);

        int result = weChatMapper.reduceCartNum(orderDetailId,productNumber,totalPrice);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","成功！");
        }else {
            return FrontUtil.returnToFront(false,"","失败！");
        }

    }

    @Override
    public List<Map> orderStatistics() {
        Information information = LoginPlugin.getLoginUserModel();
        String customerId = information.getUserId();
        return weChatMapper.orderStatistics(customerId);
    }

    @Override
    public List<Map> userStatistics() {
        return weChatMapper.userStatistics();
    }

    @Override
    public Map<String, Object> warehouseI(Map<String, String> map) {
        Information information = LoginPlugin.getLoginUserModel();
        map.put("userId",information.getUserId());
        List<Map> list = weChatMapper.warehouseI(map);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"成功！");
        }else {
            return FrontUtil.returnToFront(false,"","失败！");
        }
    }

    @Override
    public Map<String, Object> warehouseIs(Map<String, String> map) {
        Information information = LoginPlugin.getLoginUserModel();
        map.put("userId",information.getUserId());
        List<Map> list = weChatMapper.warehouseIs(map);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"成功！");
        }else {
            return FrontUtil.returnToFront(false,"","失败！");
        }
    }
}
