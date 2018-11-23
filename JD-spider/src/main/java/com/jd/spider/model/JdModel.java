package com.jd.spider.model;

import java.io.Serializable;

public class JdModel implements Serializable {


    private static final long serialVersionUID = -9081259429719031051L;


    private String bookID;
    private String bookName;
    private String bookPrice;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public String toString() {
        return "JdModel{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPrice='" + bookPrice + '\'' +
                '}';
    }
}
