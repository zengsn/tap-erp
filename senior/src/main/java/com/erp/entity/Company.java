package com.erp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("erp_company")
public class Company implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId
    private String companyId;

    private String companyName;
    private String companyRegisterNum;
    private String companyNature;
    private String companyGeneralManager;
    private String companyTel;
    private String companyAddress;
    private String companyBusiness;
    private String enables;

    public Company() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRegisterNum() {
        return companyRegisterNum;
    }

    public void setCompanyRegisterNum(String companyRegisterNum) {
        this.companyRegisterNum = companyRegisterNum;
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature;
    }

    public String getCompanyGeneralManager() {
        return companyGeneralManager;
    }

    public void setCompanyGeneralManager(String companyGeneralManager) {
        this.companyGeneralManager = companyGeneralManager;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyBusiness() {
        return companyBusiness;
    }

    public void setCompanyBusiness(String companyBusiness) {
        this.companyBusiness = companyBusiness;
    }

    public String getEnables() {
        return enables;
    }

    public void setEnables(String enables) {
        this.enables = enables;
    }
}
