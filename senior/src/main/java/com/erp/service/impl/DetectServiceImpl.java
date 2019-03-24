package com.erp.service.impl;

import com.erp.common.MailState;
import com.erp.dao.DetectMapper;
import com.erp.plugin.MailNotify;
import com.erp.service.DetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("detectService")
public class DetectServiceImpl implements DetectService{

    @Autowired
    @Qualifier("detectMapper")
    private DetectMapper detectMapper;

    @Override
    public List<Map> getPurchaseItemList() {
        return detectMapper.getPurchaseItemList();
    }

    @Override
    @MailNotify(value = MailState.PURCHASE_OVERDUE)
    public boolean updatePurchaseItemState(String purchaseItemId,String purchaseItemName,String purchaseState,String recipient) {
        int result = detectMapper.updatePurchaseItemState(purchaseItemId,purchaseState);
        if(result > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Map getUserMail(String userId) {
        return detectMapper.getUserMail(userId);
    }
}
