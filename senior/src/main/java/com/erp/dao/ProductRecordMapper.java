package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.erp.entity.ProductRecord;
import org.springframework.stereotype.Repository;

@Repository("productRecordMapper")
public interface ProductRecordMapper extends AutoMapper<ProductRecord>{

    int insertProductRecord(ProductRecord productRecord);

}
