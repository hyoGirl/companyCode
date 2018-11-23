package com.jd.spider.model;

public class DangDangModel {


    private static final long serialVersionUID = -9081259429719031051L;


    private String watchID;
    private String watchName;
    private double watchPrice;

    public String getWatchID() {
        return watchID;
    }

    public void setWatchID(String watchID) {
        this.watchID = watchID;
    }

    public String getWatchName() {
        return watchName;
    }

    public void setWatchName(String watchName) {
        this.watchName = watchName;
    }

    public double getWatchPrice() {
        return watchPrice;
    }

    public void setWatchPrice(double watchPrice) {
        this.watchPrice = watchPrice;
    }

    @Override
    public String toString() {
        return "DangDangModel{" +
                "watchID='" + watchID + '\'' +
                ", watchName='" + watchName + '\'' +
                ", watchPrice=" + watchPrice +
                '}';
    }
}
