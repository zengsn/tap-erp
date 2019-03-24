package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.erp.entity.PurchaseItemList;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("purchaseItemListMapper")
public interface PurchaseItemListMapper extends AutoMapper<PurchaseItemList>{
    int insertPurchaseItemList(PurchaseItemList purchaseItemList);
    List<PurchaseItemList> getPurchaseItemList(String purchaseItemId);
}
