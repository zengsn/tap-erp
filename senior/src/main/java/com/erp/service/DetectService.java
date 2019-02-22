package com.erp.service;

import java.util.List;
import java.util.Map;

public interface DetectService {

    List<Map> getPurchaseItemList();

    boolean updatePurchaseItemState(String purchaseItemId,String purchaseItemName,String purchaseState,String recipient);

    Map getUserMail(String userId);
}
