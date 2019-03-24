package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.erp.common.FrontUtil;
import com.erp.common.MailState;
import com.erp.dao.ProductTypeMapper;
import com.erp.entity.Information;
import com.erp.entity.ProductType;
import com.erp.plugin.LoginPlugin;
import com.erp.plugin.RedisCacheManager;
import com.erp.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("productTypeService")
public class ProductTypeServiceImpl extends SuperServiceImpl<ProductTypeMapper,ProductType> implements ProductTypeService{

    @Autowired
    @Qualifier("productTypeMapper")
    private ProductTypeMapper productTypeMapper;

//    @Autowired
//    @Qualifier("restTemplate")
//    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> addSubType(ProductType productType) {
        Information information = LoginPlugin.getLoginUserModel();
        String productTypeId = UUID.randomUUID().toString();
        String productCode = productTypeId.split("-")[1];
        productType.setProductTypeId(productTypeId);
        productType.setProductCode(productCode);
        productType.setRemark("");
        productType.setEnables("1");
        productType.setCompanyName(information.getCompanyName());
        try {
            insert(productType);
            return FrontUtil.returnToFront(true,"","添加子类型成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","添加子类型失败！");
        }
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Map<String, Object> deleteType(ProductType productType) {
        if(productType.getProductTypeParentId().equals("")){
            productType.setProductTypeParentId("0");
        }
        if(!productType.getProductTypeParentId().equals("0")){
            try {
                deleteSelective(productType);
                return FrontUtil.returnToFront(true,"","删除子类型成功！");
            }catch (Exception e){
                e.printStackTrace();
                return FrontUtil.returnToFront(false,"","删除子类型失败！");
            }
        }else{
            try {
                List<ProductType> list = productTypeMapper.getByProductTypeParentId(productType.getProductTypeId());
                for(int i=0;i<list.size();i++){
                    deleteSelective(list.get(i));
                }
                deleteSelective(productType);
                return FrontUtil.returnToFront(true,"","删除所有类型成功！");
            }catch (Exception e){
                e.printStackTrace();
                return FrontUtil.returnToFront(false,"","删除子类型失败！");
            }
        }
    }

    @Override
    public List<Map> getSimpleData() throws Exception{
        Information information = LoginPlugin.getLoginUserModel();
        List<Map> list = productTypeMapper.getSimpleData(information.getCompanyName());
        return list;
    }

    @Override
    public Map<String, Object> addBusinessType(ProductType productType) {
        Information information = LoginPlugin.getLoginUserModel();
        String productTypeId = UUID.randomUUID().toString();
        String productCode = productTypeId.split("-")[1];
        productType.setProductTypeId(productTypeId);
        productType.setProductTypeParentId("0");
        productType.setProductCode(productCode);
        productType.setCompanyName(information.getCompanyName());
        productType.setEnables("1");
        try {
            insert(productType);
            return FrontUtil.returnToFront(true,"","添加业务类型成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","添加业务类型失败！");
        }
    }
}
