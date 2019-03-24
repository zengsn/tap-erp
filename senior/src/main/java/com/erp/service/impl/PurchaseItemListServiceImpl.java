package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.erp.common.DefineUtil;
import com.erp.common.FrontUtil;
import com.erp.dao.PurchaseItemListMapper;
import com.erp.dao.PurchaseItemMapper;
import com.erp.entity.PurchaseItem;
import com.erp.entity.PurchaseItemList;
import com.erp.service.PurchaseItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("purchaseItemListService")
public class PurchaseItemListServiceImpl extends SuperServiceImpl<PurchaseItemListMapper,PurchaseItemList> implements PurchaseItemListService{

    @Autowired
    @Qualifier("purchaseItemListMapper")
    private PurchaseItemListMapper purchaseItemListMapper;

    @Autowired
    @Qualifier("purchaseItemMapper")
    private PurchaseItemMapper purchaseItemMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String, Object> insertPurchaseItemList(PurchaseItemList purchaseItemList) {
        purchaseItemList.setListId(DefineUtil.getFormatPurchaseItemListId());

        Map<String,Object> selectMap = new HashMap<>();
        selectMap.put("purchaseItemId",purchaseItemList.getPurchaseItemId());

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setPurchaseItemId(purchaseItemList.getPurchaseItemId());
        List<PurchaseItemList> itemLists = null;

        try {
            purchaseItem = purchaseItemMapper.selectOne(purchaseItem);
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","获取采购项目失败！");
        }
        try {
            itemLists = selectByMap(selectMap);
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","获取采购清单失败！");
        }

        BigDecimal budget = purchaseItem.getBudget();//项目金额
        BigDecimal unitPrice = purchaseItemList.getUnitPrice();//清单采购价
        String productQuantity = purchaseItemList.getProductQuantity();//清单采购数量

        if(itemLists.size()==0){
            if(purchaseItem.getBudgetUnit().equals(purchaseItemList.getBudgetUnit())){
                if(budget.compareTo(unitPrice.multiply(new BigDecimal(productQuantity))) != -1){
                    try {
                        insert(purchaseItemList);
                        return FrontUtil.returnToFront(true,"","新增采购清单成功！");
                    }catch (Exception e){
                        e.printStackTrace();
                        return FrontUtil.returnToFront(false,"","新增采购清单失败！");
                    }
                }else{
                    return FrontUtil.returnToFront(false,"","清单总金额大于项目金额，新增失败！");
                }
            }else {
                return FrontUtil.returnToFront(false,"","清单的金额单位必须与项目金额单位一致！");
            }
        }else {
            if(purchaseItem.getBudgetUnit().equals(purchaseItemList.getBudgetUnit())){
                BigDecimal unitPriceList = new BigDecimal(0);//清单采购价
                BigDecimal remain = new BigDecimal(0);//剩余预算金额
                for(int i=0;i<itemLists.size();i++){
                    unitPriceList = unitPriceList.add(itemLists.get(i).getUnitPrice().multiply(new BigDecimal(itemLists.get(i).getProductQuantity())));
                }
                remain = budget.subtract(unitPriceList);
                if(remain.compareTo(unitPrice.multiply(new BigDecimal(productQuantity))) != -1){
                    try {
                        insert(purchaseItemList);
                        return FrontUtil.returnToFront(true,"","新增采购清单成功!");
                    }catch (Exception e){
                        e.printStackTrace();
                        return FrontUtil.returnToFront(false,"","新增采购清单失败!");
                    }
                }else {
                    return FrontUtil.returnToFront(false,"","剩余的金额预算还有！" + remain + purchaseItemList.getBudgetUnit());
                }
            }else {
                return FrontUtil.returnToFront(false,"","清单的金额单位必须与项目金额单位一致！");
            }
        }
    }

    @Override
    public Map<String, Object> getPurchaseItemList(String purchaseItemId) {
        List<PurchaseItemList> purchaseItemList = purchaseItemListMapper.getPurchaseItemList(purchaseItemId);
        if (purchaseItemList != null) {
            return FrontUtil.returnToFront(true,purchaseItemList,"查询采购清单成功！");
        }else{
            return FrontUtil.returnToFront(false,"","查询采购清单失败！");
        }
    }
}
