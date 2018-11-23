package com.jd.spider.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class HTTPUtils {


    public static HttpResponse getRawHtml(HttpClient client, String url) throws IOException {

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);

        return response;
    }
}