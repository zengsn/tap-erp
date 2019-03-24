package com.erp.controller;

import com.erp.entity.ProductType;
import com.erp.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("/tree")
@Controller
public class TreeController {

    @Autowired
    @Qualifier("productTypeService")
    private ProductTypeService productTypeService;

    @RequestMapping("/getSimpleData")
    @ResponseBody
    public List<Map> getSimpleData() throws Exception{
        return productTypeService.getSimpleData();
    }

    @RequestMapping("/addSubType")
    @ResponseBody
    public Map<String,Object> addType(ProductType productType){
        return productTypeService.addSubType(productType);
    }

    @RequestMapping("/deleteType")
    @ResponseBody
    public Map<String,Object> deleteType(ProductType productType){
        return productTypeService.deleteType(productType);
    }

}
