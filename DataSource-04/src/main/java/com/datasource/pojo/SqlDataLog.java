package com.datasource.pojo;



import java.io.Serializable;
import java.util.Date;

public class SqlDataLog implements Serializable {


    private static final long serialVersionUID = 1L;

    private  Long id;
    /**
     * sql方法
     */
    private  String methodname;

    /**
     * sql语句
     */
    private String sqlData;

    /**
     * 访问的url
     */
    private String url;


    /**
     * sql执行消耗时间
     */
    private Long costtime;

    /**
     * sql产生时间
      */
    private Date occurtime;

    /**
     * 插入数据库日期
     */
    private Date createtime;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCosttime() {
        return costtime;
    }

    public void setCosttime(Long costtime) {
        this.costtime = costtime;
    }

    public Date getOccurtime() {
        return occurtime;
    }

    public void setOccurtime(Date occurtime) {
        this.occurtime = occurtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSqlData() {
        return sqlData;
    }

    public void setSqlData(String sqlData) {
        this.sqlData = sqlData;
    }

    @Override
    public String toString() {
        return "SqlDataLog{" +
                "id=" + id +
                ", methodname='" + methodname + '\'' +
                ", sqlData='" + sqlData + '\'' +
                ", url='" + url + '\'' +
                ", costtime=" + costtime +
                ", occurtime=" + occurtime +
                ", createtime=" + createtime +
                '}';
    }
}
