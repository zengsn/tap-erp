package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.DefineUtil;
import com.erp.common.FrontUtil;
import com.erp.common.ProductState;
import com.erp.dao.ProductMapper;
import com.erp.dao.ProductRecordMapper;
import com.erp.entity.Company;
import com.erp.entity.Information;
import com.erp.entity.Product;
import com.erp.entity.ProductRecord;
import com.erp.plugin.LoginPlugin;
import com.erp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("productService")
public class ProductServiceImpl extends SuperServiceImpl<ProductMapper,Product> implements ProductService{

    @Autowired
    @Qualifier("productMapper")
    private ProductMapper productMapper;

    @Autowired
    @Qualifier("productRecordMapper")
    private ProductRecordMapper productRecordMapper;

    @Override
    public Page<Map<String, Object>> getProductList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        Information information = LoginPlugin.getLoginUserModel();
        paramsMap.put("companyName",information.getCompanyName());
        page.setRecords(productMapper.getProductList(page,paramsMap));
        return page;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String,Object> insertProduct(Product product, Information information) throws Exception{
        product.setProductId(DefineUtil.getFormatProductId());
        product.setSales(new BigDecimal(0));
        product.setEnables("1");
        int result ;
        try{
            result = productMapper.insertProduct(product);
        }catch (Exception e){
            throw e;

        }
        if(result > 0){
            ProductRecord productRecord = new ProductRecord();
            productRecord.setProductRecordId(DefineUtil.getFormatProductRecordId());
            productRecord.setProductId(product.getProductId());
            productRecord.setOperatorId(information.getUserId());
            productRecord.setOperator(information.getUserName());
            productRecord.setOperateTime(new Date());
            productRecord.setOperateNumber(product.getInventory());
            productRecord.setOperateSign(ProductState.PURCHASE_STORAGE.getCode());
            int tran = productRecordMapper.insertProductRecord(productRecord);
            if(tran > 0){
                return FrontUtil.returnToFront(true,"","采购入库成功！");
            }else {
                return FrontUtil.returnToFront(false,"","采购入库失败！");
            }
        }else{
            return FrontUtil.returnToFront(false,"","采购入库失败！");
        }
    }

    @Override
    @Transactional
    public Map<String, Object> updateProduct(Product product,Information information) throws Exception{
        int result;
        try {
            result = productMapper.updateProduct(product);
        }catch (Exception e){
            throw e;
        }
        try {
            if(result > 0){
                ProductRecord productRecord = new ProductRecord();
                productRecord.setProductRecordId(DefineUtil.getFormatProductRecordId());
                productRecord.setProductId(product.getProductId());
                productRecord.setOperatorId(information.getUserId());
                productRecord.setOperator(information.getUserName());
                productRecord.setOperateTime(new Date());
                productRecord.setOperateNumber(product.getInventory());
                productRecord.setOperateSign(ProductState.EDIT_PRODUCT.getCode());
                int tran = productRecordMapper.insertProductRecord(productRecord);
                if(tran > 0){
                    return FrontUtil.returnToFront(true,"","修改商品信息成功！");
                }else {
                    return FrontUtil.returnToFront(false,"","修改商品信息失败！");
                }
            }
            else{
                return FrontUtil.returnToFront(false,"","修改商品信息失败！");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional
    public Map<String, Object> adjustProductPrice(Product product, Information information) throws Exception {
        int result;
        try {
            result = productMapper.adjustProductPrice(product);
        }catch (Exception e){
            throw e;
        }
        try{
            if(result > 0){
                ProductRecord productRecord = new ProductRecord();
                productRecord.setProductRecordId(DefineUtil.getFormatProductRecordId());
                productRecord.setProductId(product.getProductId());
                productRecord.setOperatorId(information.getUserId());
                productRecord.setOperator(information.getUserName());
                productRecord.setOperateTime(new Date());
                productRecord.setOperateSign(ProductState.ADJUST_PRICE.getCode());
                int tran = productRecordMapper.insertProductRecord(productRecord);
                if(tran > 0){
                    return FrontUtil.returnToFront(true,"","价格调整成功！");
                }else {
                    return FrontUtil.returnToFront(false,"","价格调整失败！");
                }
            }
            else{
                return FrontUtil.returnToFront(false,"","价格调整失败！");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public Map<String, Object> deleteProduct(Product product, Information information) throws Exception {
        int result;
        try {
            result = productMapper.deleteProduct(product);
        }catch (Exception e){
            throw e;
        }
        try{
            if(result > 0){
                ProductRecord productRecord = new ProductRecord();
                productRecord.setProductRecordId(DefineUtil.getFormatProductRecordId());
                productRecord.setProductId(product.getProductId());
                productRecord.setOperatorId(information.getUserId());
                productRecord.setOperator(information.getUserName());
                productRecord.setOperateTime(new Date());
                productRecord.setOperateSign(ProductState.DELETE_PRODUCT.getCode());
                int tran = productRecordMapper.insertProductRecord(productRecord);
                if(tran > 0){
                    return FrontUtil.returnToFront(true,"","删除商品成功！");
                }else {
                    return FrontUtil.returnToFront(false,"","删除商品失败！");
                }
            }
            else{
                return FrontUtil.returnToFront(false,"","删除商品失败！");
            }
        }catch (Exception e){
            throw e;
        }
    }


}
