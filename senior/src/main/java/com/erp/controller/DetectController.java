package com.erp.controller;

import com.erp.common.*;
import com.erp.dao.UserMapper;
import com.erp.plugin.MailNotify;
import com.erp.service.DetectService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
public class DetectController {

    @Autowired
    @Qualifier("detectService")
    public DetectService detectService;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    public void execute() throws Exception{
        List<Map> list= detectService.getPurchaseItemList();
        for (int i=0; i<list.size(); i++){
            Date date = (Date) list.get(i).get("purchaseDeadline");
            String own = list.get(i).get("own").toString();
            String recipient = userMapper.getUserMail(list.get(i).get("purchaseApplicantId").toString());
            if(compareDate(date) && own.equals("null")){
                boolean result = detectService.updatePurchaseItemState(list.get(i).get("purchaseItemId").toString(), list.get(i).get("purchaseItemName").toString(),PurchaseState.OVERDUE.getCode(),recipient);
                if(result){
                }
            }else{
                detectService.updatePurchaseItemState(list.get(i).get("purchaseItemId").toString(), list.get(i).get("purchaseItemName").toString(),PurchaseState.WAITING_TO_AFFIRM.getCode(),recipient);
            }
        }

    }

    public static boolean compareDate(Date purchaseDeadline) throws Exception {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = format.parse(format.format(date));
        return today.after(purchaseDeadline);
    }
}
