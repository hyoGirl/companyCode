package com.request.param;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DatePerson implements Serializable{


    private static final long serialVersionUID = 2593190919066832736L;


//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")  // 将Date转换成String 一般后台传值给前台时。这个地方不能获取毫秒值
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")  //是将String转换成Date，一般前台给后台传值时用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS" ,timezone="GMT+8")  //可以把String转递给后台，但是少8个小时
    private Date time;

    private String name;

    private String time2;

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "DatePerson{" +
                "time=" + time +
                ", name='" + name + '\'' +
                ", time2='" + time2 + '\'' +
                '}';
    }
}
