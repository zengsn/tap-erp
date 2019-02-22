package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.erp.entity.PurchaseItemList;

import java.util.Map;

public interface PurchaseItemListService extends ISuperService<PurchaseItemList>{

    Map<String,Object> insertPurchaseItemList(PurchaseItemList purchaseItemList);

    Map<String,Object> getPurchaseItemList(String purchaseItemId);
}
