package com.erp.plugin;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert implements Converter<String,Date>{

    private final static SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat SIMPLEDATETIMEFORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String s) {
        try {
            if(!StringUtils.isEmpty(s)){
                if(s.length() <= 10){
                    return SIMPLEDATEFORMAT.parse(s);
                }
                else{
                    return SIMPLEDATETIMEFORMAT.parse(s);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
