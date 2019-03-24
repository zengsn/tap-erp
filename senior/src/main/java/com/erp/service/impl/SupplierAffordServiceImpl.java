package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.DefineUtil;
import com.erp.common.FrontUtil;
import com.erp.common.PurchaseState;
import com.erp.dao.PurchaseItemMapper;
import com.erp.dao.SupplierAffordMapper;
import com.erp.entity.Information;
import com.erp.entity.PurchaseItem;
import com.erp.entity.SupplierAfford;
import com.erp.plugin.LoginPlugin;
import com.erp.service.SupplierAffordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("supplierAffordService")
public class SupplierAffordServiceImpl extends SuperServiceImpl<SupplierAffordMapper,SupplierAfford> implements SupplierAffordService {

    @Autowired
    @Qualifier("supplierAffordMapper")
    private SupplierAffordMapper supplierAffordMapper;

    @Autowired
    @Qualifier("purchaseItemMapper")
    private PurchaseItemMapper purchaseItemMapper;

    @Override
    @Transactional
    public Map<String, Object> insertSupplierAfford(SupplierAfford supplierAfford, Information information) {
        supplierAfford.setAffordId(DefineUtil.getFormatSupplierAffordId());
        supplierAfford.setSupplier(information.getSupplierName());
        supplierAfford.setSupplierBehalfId(information.getUserId());
        supplierAfford.setSupplierBehalf(information.getUserName());
        supplierAfford.setSupplierBehalfContact(information.getPhone());
        supplierAfford.setAffirmAfford("0");
        try{
            supplierAffordMapper.insertSupplierAfford(supplierAfford);
            return FrontUtil.returnToFront(true,"","编辑供应内容成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","编辑供应内容失败！");
        }
    }

    @Override
    public Map<String, Object> getSupplierAfford(String purchaseItemId) {
        Information information = LoginPlugin.getLoginUserModel();
        Map<String,String> map = new HashMap<>();
        map.put("purchaseItemId",purchaseItemId);
        map.put("supplier",information.getSupplierName());
        List<SupplierAfford> supplierAfford = supplierAffordMapper.getSupplierAfford(map);
        if(supplierAfford != null){
            return FrontUtil.returnToFront(true,supplierAfford,"查询供应信息成功！");
        }else{
            return FrontUtil.returnToFront(false,"","查询供应信息失败！");
        }
    }

    @Override
    public Map<String, Object> getAffirmSupplierAfford(String purchaseItemId) {
        Map<String,String> map = new HashMap<>();
        map.put("purchaseItemId",purchaseItemId);
        List<SupplierAfford> supplierAfford = supplierAffordMapper.getAffirmSupplierAfford(map);
        if(supplierAfford != null){
            return FrontUtil.returnToFront(true,supplierAfford,"查询供应信息成功！");
        }else{
            return FrontUtil.returnToFront(false,"","查询供应信息失败！");
        }
    }

    @Override
    public Page<Map<String, Object>> getOwnSupplierAffordList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        Information information = LoginPlugin.getLoginUserModel();
        paramsMap.put("supplier",information.getSupplierName());
        page.setRecords(supplierAffordMapper.getOwnSupplierAffordList(page,paramsMap));
        return page;
    }
}


