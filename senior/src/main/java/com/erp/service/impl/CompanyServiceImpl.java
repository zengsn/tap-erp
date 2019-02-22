package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.dao.CompanyMapper;
import com.erp.entity.Company;
import com.erp.entity.Supplier;
import com.erp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service("companyService")
public class CompanyServiceImpl extends SuperServiceImpl<CompanyMapper,Company> implements CompanyService {

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Override
    public Page<Map<String, Object>> getCompanyList(Page<Map<String, Object>> page, Map<String, String> params) {
        page.setRecords(companyMapper.getCompanyList(page,params));
        return page;
    }

    @Override
    public Map<String, Object> addCompany(Company company) {
        company.setCompanyId(UUID.randomUUID().toString());
        company.setEnables("1");
        try {
            insert(company);
            return FrontUtil.returnToFront(true,"","添加公司成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","添加公司失败！");
        }
    }

    @Override
    public Map<String, Object> deleteCompanyById(Company company){
        try {
            deleteSelective(company);
            return FrontUtil.returnToFront(true,"","删除公司成功！");
        }catch (Exception e){
            e.printStackTrace();
            return FrontUtil.returnToFront(false,"","删除公司失败！");
        }
    }
}
