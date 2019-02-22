package com.erp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
@TableName("erp_product_type")
public class ProductType implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId
    private String productTypeId;
    private String productTypeParentId;
    private String productCode;
    private String productTypeName;
    private String remark;
    private String companyName;
    private String enables;


    public ProductType() {
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeParentId() {
        return productTypeParentId;
    }

    public void setProductTypeParentId(String productTypeParentId) {
        this.productTypeParentId = productTypeParentId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEnables() {
        return enables;
    }

    public void setEnables(String enables) {
        this.enables = enables;
    }
}
