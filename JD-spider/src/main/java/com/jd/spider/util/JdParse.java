package com.jd.spider.util;

import com.jd.spider.model.JdModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JdParse {


    public static List<JdModel> getData(String html){

        List<JdModel> data = new ArrayList<JdModel>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
        for (Element element : elements) {

            //attr  获取当前属性值
            String bookID = element.attr("data-sku");
            //text 获取文本值
            String bookPrice = element.select("div[class=p-price]").select("strong").select("i").text();

            String bookName = element.select("div[class=p-name]").select("em").text();

            JdModel jdModel=new JdModel();

            jdModel.setBookID(bookID);
            jdModel.setBookName(bookName);
            jdModel.setBookPrice(bookPrice);
            data.add(jdModel);

        }
        return data;
    }
}
