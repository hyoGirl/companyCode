package com.spring.valid.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class CallRecord2 implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 通话号码
     */
    private String mobile;

    /**
     * 通话时长(秒)
     */
    private int billSec;

    /**
     * 拨打时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date callDate;

    /**
     * 销售手机号码
     */
    private String empWorkMobile;

    /**
     * 销售编号
     */
    private String empCode;

    private int callStatus;

    /**
     * 拨打者
     */
    private String src;

    /**
     * 接受者
     */
    private String dst;

    /**
     * 客户编号
     */
    private String customerCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS" ,timezone="GMT+8")
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getBillSec() {
        return billSec;
    }

    public void setBillSec(int billSec) {
        this.billSec = billSec;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getEmpWorkMobile() {
        return empWorkMobile;
    }

    public void setEmpWorkMobile(String empWorkMobile) {
        this.empWorkMobile = empWorkMobile;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public int getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "CallRecord{" +
                "mobile='" + mobile + '\'' +
                ", billSec=" + billSec +
                ", callDate=" + callDate +
                ", empWorkMobile='" + empWorkMobile + '\'' +
                ", empCode='" + empCode + '\'' +
                ", callStatus=" + callStatus +
                ", src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
