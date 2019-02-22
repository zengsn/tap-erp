package com.erp.common;

public enum MailState {

    PURCHASE_OVERDUE("purchaseOverdueSend","purchaseOverdueSend"),
    AFFIRM_SUPPLIER("purchaseOverdueSend","affirmPurchaseSend");

    private String value;
    private String methodName;


    private MailState(String value,String methodName){
        this.value = value;
        this.methodName = methodName;
    }

    public String getValue(){
        return value;
    }

    public String getMethodName(){
        return methodName;
    }
}
