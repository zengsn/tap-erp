package com.erp.common;

public enum OrderState {
    //WAITING_PAY("0","待结算"),
    WAITING_DELIVER("1","待发货"),
    SHIPPED("2","待收货"),
    WAITING_RETURN("3","退货中"),
    TRADE_FINISHED("4","交易完成");

    private String code;
    private String description;

    private OrderState(String code,String description){
        this.code = code;
        this.description =description;
    }

    public String getCode(){
        return code;
    }
}
