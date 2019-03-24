package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.*;
import com.erp.dao.PurchaseItemMapper;
import com.erp.dao.SupplierAffordMapper;
import com.erp.dao.UserMapper;
import com.erp.entity.Information;
import com.erp.entity.PurchaseItem;
import com.erp.plugin.LoginPlugin;
import com.erp.plugin.MailNotify;
import com.erp.service.PurchaseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service("purchaseItemService")
public class PurchaseItemServiceImpl extends SuperServiceImpl<PurchaseItemMapper,PurchaseItem> implements PurchaseItemService{

    @Autowired
    @Qualifier("purchaseItemMapper")
    private PurchaseItemMapper purchaseItemMapper;

    @Autowired
    @Qualifier("supplierAffordMapper")
    private SupplierAffordMapper supplierAffordMapper;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

//    @Autowired
//    @Qualifier("restTemplate")
//    private RestTemplate restTemplate;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String, Object> insertPurchaseItem(PurchaseItem purchaseItem, Information information) {
        purchaseItem.setPurchaseItemId(DefineUtil.getFormatPurchaseItemId());
        purchaseItem.setPurchaseApplicantId(information.getUserId());
        purchaseItem.setPurchaseApplicant(information.getUserName());
        purchaseItem.setPurchaseApplicantContact(information.getPhone());
        purchaseItem.setPurchaseState(PurchaseState.WAITING_TO_BUILD_ITEM_LIST.getCode());
        purchaseItem.setEnables("1");
        try {
            insert(purchaseItem);
            return FrontUtil.returnToFront(true,"","新增采购申请成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","新增采购申请失败！");
        }
    }

    @Override
    public Page<Map<String, Object>> getPurchaseItemList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        Information information = LoginPlugin.getLoginUserModel();
        paramsMap.put("companyName",information.getCompanyName());
        paramsMap.put("departmentName",information.getDepartmentName());
        page.setRecords(purchaseItemMapper.getPurchaseItemList(page,paramsMap));
        return page;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String, Object> updatePurchaseItem(PurchaseItem purchaseItem) {
        int result = purchaseItemMapper.updatePurchaseItem(purchaseItem);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","修改采购申请成功！");
        }else{
            return FrontUtil.returnToFront(false,"","取消采购申请失败！");
        }
    }

    @Override
    public Map<String, Object> canclePurchase(String purchaseItemId) {
        int result = purchaseItemMapper.canclePurchase(purchaseItemId);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","取消采购成功！");
        }else{
            return FrontUtil.returnToFront(false,"","取消采购申请失败！");
        }
    }

    @Override
    public Map<String, Object> finishInsertPurchaseItemList(String purchaseItemId) {
        String purchaseState = PurchaseState.PURCHASING.getCode();
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("purchaseItemId",purchaseItemId);
        paramsMap.put("purchaseState",purchaseState);
        int result = purchaseItemMapper.finishInsertPurchaseItemList(paramsMap);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","完成采购成功！");
        }else{
            return FrontUtil.returnToFront(false,"","完成采购失败！");
        }
    }

    @Override
    public Page<Map<String,Object>> getSupplierPurchaseItemList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        page.setRecords(purchaseItemMapper.getSupplierPurchaseItemList(page,paramsMap));
        return page;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @MailNotify(value = MailState.AFFIRM_SUPPLIER)
    public Map<String, Object> affirmPurchase(Map<String,String> map) {
        map.put("purchaseState",PurchaseState.WAITING_TO_STOCK.getCode());
        try {
            purchaseItemMapper.updatePurchaseState(map);
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","选择供应商失败！");
        }
        try {
            supplierAffordMapper.affirmAfford(map.get("affordId"));
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","确认供应商失败！");
        }
        return FrontUtil.returnToFront(true,"","确认供应商成功！");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String, Object> affirmReceive(String purchaseItemId) {
        Map<String,String> map = new HashMap<>();
        map.put("purchaseItemId",purchaseItemId);
        map.put("purchaseState",PurchaseState.FINISHED.getCode());
        int result = purchaseItemMapper.updatePurchaseState(map);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","确认收货成功！");
        }else{
            return FrontUtil.returnToFront(false,"","确认收货失败！");
        }
    }

    @Override
    public PurchaseItem getSinglePurchaseItem(String purchaseItemId) {
        return purchaseItemMapper.getSinglePurchaseItem(purchaseItemId);
    }

    @Override
    public Page<Map<String, Object>> getSupplierAffordByPurchaseItemId(Page<Map<String, Object>> page, String purchaseItemId) {
        page.setRecords(purchaseItemMapper.getSupplierAffordByPurchaseItemId(page,purchaseItemId));
        return page;
    }

    @Override
    public Page<Map<String, Object>> getSupplierAffirmAffordByPurchaseItemId(Page<Map<String, Object>> page, String purchaseItemId) {
        page.setRecords(purchaseItemMapper.getSupplierAffirmAffordByPurchaseItemId(page,purchaseItemId));
        return page;
    }
}
