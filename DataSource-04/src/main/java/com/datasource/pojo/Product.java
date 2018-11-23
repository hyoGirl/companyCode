package com.datasource.pojo;


import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 产品ID
     */
    private  Long id;

    /**
     * 产品名称
     */
    private  String name;
    /**
     * 产品数量
     */
    private  int count;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 产品生产日期
     */
    private Date productionDate;

    /**
     * 产品过期时间
     */
    private Date expireDate;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", description='" + description + '\'' +
                ", productionDate=" + productionDate +
                ", expireDate=" + expireDate +
                '}';
    }
}
