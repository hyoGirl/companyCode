package com.jd.spider.db;

import com.jd.spider.model.JdModel;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class MYSQLControl {


    private static Logger logger= LoggerFactory.getLogger(MYSQLControl.class);

     static DataSource dataSource = MyDataSource.getDataSource();

    static QueryRunner qr = new QueryRunner(dataSource);

    public static void executeInsert(List<JdModel> jingdongdata) {


        //使用二维数组来创建数据.第一个是图书容量，第二个是单独的数组
       Object[][] params=new Object[jingdongdata.size()][3];
       for(int i=0;i<params.length;i++){
           params[i][0]=jingdongdata.get(i).getBookID();
           params[i][1]=jingdongdata.get(i).getBookName();
           params[i][2]=jingdongdata.get(i).getBookPrice();
       }

       String sql="insert into JDModel(bookID,bookName,bookPrice) values(?,?,?)";

        try {
            qr.batch(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("数据库插入成功条数为："+jingdongdata.size());

    }
}
