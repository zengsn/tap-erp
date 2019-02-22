package com.erp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
@TableName("erp_supplier_afford")
public class SupplierAfford implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId
    private String affordId;

    private String purchaseItemId;

    private String supplier;
    private String supplierBehalfId;
    private String supplierBehalf;
    private String supplierBehalfContact;

    private String arrivalDate;
    private String guaranteeContent;

    private String affirmAfford;

    public SupplierAfford() {
    }

    public String getAffordId() {
        return affordId;
    }

    public void setAffordId(String affordId) {
        this.affordId = affordId;
    }

    public String getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(String purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierBehalfId() {
        return supplierBehalfId;
    }

    public void setSupplierBehalfId(String supplierBehalfId) {
        this.supplierBehalfId = supplierBehalfId;
    }

    public String getSupplierBehalf() {
        return supplierBehalf;
    }

    public void setSupplierBehalf(String supplierBehalf) {
        this.supplierBehalf = supplierBehalf;
    }

    public String getSupplierBehalfContact() {
        return supplierBehalfContact;
    }

    public void setSupplierBehalfContact(String supplierBehalfContact) {
        this.supplierBehalfContact = supplierBehalfContact;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getGuaranteeContent() {
        return guaranteeContent;
    }

    public void setGuaranteeContent(String guaranteeContent) {
        this.guaranteeContent = guaranteeContent;
    }

    public String getAffirmAfford() {
        return affirmAfford;
    }

    public void setAffirmAfford(String affirmAfford) {
        this.affirmAfford = affirmAfford;
    }
}
