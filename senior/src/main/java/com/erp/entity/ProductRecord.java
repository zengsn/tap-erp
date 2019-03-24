package com.erp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName("erp_product_record")
public class ProductRecord implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId
    private String productRecordId;
    private String productId;
    private String operatorId;
    private String operator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;
    private BigDecimal operateNumber;
    private String operateSign;

    public ProductRecord() {
    }

    public String getProductRecordId() {
        return productRecordId;
    }

    public void setProductRecordId(String productRecordId) {
        this.productRecordId = productRecordId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public BigDecimal getOperateNumber() {
        return operateNumber;
    }

    public void setOperateNumber(BigDecimal operateNumber) {
        this.operateNumber = operateNumber;
    }

    public String getOperateSign() {
        return operateSign;
    }

    public void setOperateSign(String operateSign) {
        this.operateSign = operateSign;
    }
}
