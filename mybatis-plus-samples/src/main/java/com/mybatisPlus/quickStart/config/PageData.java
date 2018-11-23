package com.mybatisPlus.quickStart.config;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

public class PageData<T> {


    public int total;


    public List<T> rows;


    public PageData(Page page) {


        this.total = page.getTotal();
        this.rows = page.getRecords();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
