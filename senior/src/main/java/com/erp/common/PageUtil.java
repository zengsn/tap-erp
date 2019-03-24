package com.erp.common;

import com.baomidou.mybatisplus.plugins.Page;

public class PageUtil<T> {

    private int current = 1;

    private int pageSize = 10;

    public Page<T> getPage(){
        return new Page<T>(current,pageSize);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
