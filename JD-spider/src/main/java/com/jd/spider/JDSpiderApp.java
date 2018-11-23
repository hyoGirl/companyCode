package com.jd.spider;

import com.jd.spider.db.MYSQLControl;
import com.jd.spider.model.JdModel;
import com.jd.spider.util.URLFecter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class JDSpiderApp {


    static final Logger logger = LoggerFactory.getLogger(JDSpiderApp.class);

    public static void main(String[] args) {
        SpringApplication.run(JDSpiderApp.class, args);
        spider();

    }

    public static void spider() {

        HttpClient httpClient = HttpClients.createDefault();

        List<JdModel> bookdatas = null;
        String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";

        try {
            bookdatas = URLFecter.URLParser(httpClient, url);
            for (JdModel bookdata : bookdatas) {
                logger.info(bookdata.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MYSQLControl.executeInsert(bookdatas);
    }
}
