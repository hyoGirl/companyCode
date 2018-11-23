package com.login.only.modules.model;

import java.io.Serializable;

public class RequestData implements Serializable{


    private static final long serialVersionUID = -6790477675329457951L;

    private String client_ip;


    private String  method_name;

    private String contentType;

    private String url;

    private String http_method;

    private String http_header;

    private Object http_body;

    private long cost_time;


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttp_method() {
        return http_method;
    }

    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }

    public String getHttp_header() {
        return http_header;
    }

    public void setHttp_header(String http_header) {
        this.http_header = http_header;
    }

    public long getCost_time() {
        return cost_time;
    }

    public void setCost_time(long cost_time) {
        this.cost_time = cost_time;
    }


    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public Object getHttp_body() {
        return http_body;
    }

    public void setHttp_body(Object http_body) {
        this.http_body = http_body;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "client_ip='" + client_ip + '\'' +
                ", method_name='" + method_name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", url='" + url + '\'' +
                ", http_method='" + http_method + '\'' +
                ", http_header='" + http_header + '\'' +
                ", http_body=" + http_body +
                ", cost_time=" + cost_time +
                '}';
    }
}
