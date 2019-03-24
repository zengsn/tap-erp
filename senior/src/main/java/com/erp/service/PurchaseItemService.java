package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.Information;
import com.erp.entity.Product;
import com.erp.entity.PurchaseItem;

import java.util.List;
import java.util.Map;

public interface PurchaseItemService extends ISuperService<PurchaseItem>{

    Map<String,Object> insertPurchaseItem(PurchaseItem purchaseItem, Information information);

    Page<Map<String,Object>> getPurchaseItemList(Page<Map<String,Object>> page, Map<String,String> paramsMap);

    Map<String,Object> updatePurchaseItem(PurchaseItem purchaseItem);
    Map<String,Object> canclePurchase(String purchaseItemId);
    Map<String,Object> finishInsertPurchaseItemList(String purchaseItemId);

    Page<Map<String,Object>> getSupplierPurchaseItemList(Page<Map<String,Object>> page, Map<String,String> paramsMap);

    Map<String,Object> affirmPurchase(Map<String,String> map);

    Map<String,Object> affirmReceive(String purchaseItemId);

    PurchaseItem getSinglePurchaseItem(String purchaseItemId);

    Page<Map<String,Object>> getSupplierAffordByPurchaseItemId(Page<Map<String,Object>> page,String purchaseItemId);
    Page<Map<String,Object>> getSupplierAffirmAffordByPurchaseItemId(Page<Map<String,Object>> page,String purchaseItemId);
}
