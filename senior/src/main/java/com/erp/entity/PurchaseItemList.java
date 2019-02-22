package com.erp.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
@TableName("erp_purchase_item_list")
public class PurchaseItemList implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId
    private String listId;

    private String purchaseItemId;

    private String productTypeName;
    private String productName;

    private String productSpecification;

    private String productQuantity;
    private String productUnit;

    private BigDecimal unitPrice;
    private String budgetUnit;

    public PurchaseItemList() {
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(String purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getBudgetUnit() {
        return budgetUnit;
    }

    public void setBudgetUnit(String budgetUnit) {
        this.budgetUnit = budgetUnit;
    }
}
