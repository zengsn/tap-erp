package com.erp.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefineUtil {
    public static String getDateString(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getFormatPurchaseItemId(){
        return "CG" + getDateString();
    }

    public static String getFormatApproveId(){
        return "SP" + getDateString();
    }

    public static String getFormatCompactId(){
        return "HT" + getDateString();
    }

    public static String getFormatPurchaseItemListId(){
        return "QD" + getDateString();
    }

    public static String getFormatSupplierAffordId(){
        return "GY" + getDateString();
    }

    public static String getFormatProductId(){
        return "SP" + getDateString();
    }

    public static String getFormatProductRecordId(){
        return "JL" + getDateString();
    }

    public static String getFormatOrderId(){
        return "DD" + getDateString();
    }

    public static String getFormatOrderDetailId(){
        return "XQ" + getDateString();
    }

    public static String getUploadProductId(){
        return getDateString() + String.valueOf(Math.round(Math.random()*8999 +1000));
    }
}
