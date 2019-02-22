package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.dao.SupplierMapper;
import com.erp.entity.Information;
import com.erp.entity.Supplier;
import com.erp.plugin.LoginPlugin;
import com.erp.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service("supplierService")
public class SupplierServiceImpl extends SuperServiceImpl<SupplierMapper,Supplier> implements SupplierService {

    @Autowired
    @Qualifier("supplierMapper")
    private SupplierMapper supplierMapper;

    @Override
    public Page<Map<String, Object>> getSupplierList(Page<Map<String, Object>> page, Map<String, String> params) {
        page.setRecords(supplierMapper.getSupplierList(page,params));
        return page;
    }

    @Override
    public Map<String,Object> addSupplier(Supplier supplier){
        supplier.setSupplierId(UUID.randomUUID().toString());
        supplier.setEnables("1");
        try {
            insert(supplier);
            return FrontUtil.returnToFront(true,"","添加供应商成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","添加供应商失败！");
        }
    }

    @Override
    public Page<Map<String, Object>> getPurchaseItemList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        Information information = LoginPlugin.getLoginUserModel();
        paramsMap.put("supplier",information.getSupplierName());
        page.setRecords(supplierMapper.getPurchaseItemList(page,paramsMap));
        return page;
    }
}
