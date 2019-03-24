package com.erp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName("erp_purchase_item")
public class PurchaseItem implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId
    private String purchaseItemId;

    private String purchaseItemName;

    private String purchaseType;

    private BigDecimal budget;
    private String budgetUnit;

    private String purchaseApplicantId;
    private String purchaseApplicant;
    private String purchaseApplicantContact;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseApplyTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDeadline;

    private String purchaseItemContent;

    private String purchaseState;

    private String affordId;

    private String enables;

    @TableField(exist = false)
    private Date arrivalDate;
    @TableField(exist = false)
    private String guaranteeContent;

    public PurchaseItem() {
    }

    public String getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(String purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public String getPurchaseItemName() {
        return purchaseItemName;
    }

    public void setPurchaseItemName(String purchaseItemName) {
        this.purchaseItemName = purchaseItemName;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getBudgetUnit() {
        return budgetUnit;
    }

    public void setBudgetUnit(String budgetUnit) {
        this.budgetUnit = budgetUnit;
    }

    public String getAffordId() {
        return affordId;
    }

    public void setAffordId(String affordId) {
        this.affordId = affordId;
    }

    public String getPurchaseApplicant() {
        return purchaseApplicant;
    }

    public void setPurchaseApplicant(String purchaseApplicant) {
        this.purchaseApplicant = purchaseApplicant;
    }

    public String getPurchaseApplicantContact() {
        return purchaseApplicantContact;
    }

    public void setPurchaseApplicantContact(String purchaseApplicantContact) {
        this.purchaseApplicantContact = purchaseApplicantContact;
    }

    public Date getPurchaseApplyTime() {
        return purchaseApplyTime;
    }

    public void setPurchaseApplyTime(Date purchaseApplyTime) {
        this.purchaseApplyTime = purchaseApplyTime;
    }

    public Date getPurchaseDeadline() {
        return purchaseDeadline;
    }

    public void setPurchaseDeadline(Date purchaseDeadline) {
        this.purchaseDeadline = purchaseDeadline;
    }

    public String getPurchaseItemContent() {
        return purchaseItemContent;
    }

    public void setPurchaseItemContent(String purchaseItemContent) {
        this.purchaseItemContent = purchaseItemContent;
    }

    public String getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(String purchaseState) {
        this.purchaseState = purchaseState;
    }

    public String getPurchaseApplicantId() {
        return purchaseApplicantId;
    }

    public void setPurchaseApplicantId(String purchaseApplicantId) {
        this.purchaseApplicantId = purchaseApplicantId;
    }

    public String getEnables() {
        return enables;
    }

    public void setEnables(String enables) {
        this.enables = enables;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getGuaranteeContent() {
        return guaranteeContent;
    }

    public void setGuaranteeContent(String guaranteeContent) {
        this.guaranteeContent = guaranteeContent;
    }
}
