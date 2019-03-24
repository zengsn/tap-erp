package com.erp.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("detectMapper")
public interface DetectMapper {

    List<Map> getPurchaseItemList();

    int updatePurchaseItemState(@Param("purchaseItemId") String purchaseItemId, @Param("purchaseState") String purchaseState);

    Map getUserMail(String userId);
}
