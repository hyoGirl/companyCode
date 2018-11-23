package com.login.log.modules.model;

import java.io.Serializable;

public class RequestLogData implements Serializable{


    private static final long serialVersionUID = -6790477675329457951L;

    private String clientIp;


    private String  methodName;

    private String contentType;
    private String userAgent;


    private String uri;

    private String httpMethod;

    private String authorization;

    private Object httpBody;

    private long costTime;


    private String cityCode;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Object getHttpBody() {
        return httpBody;
    }

    public void setHttpBody(Object httpBody) {
        this.httpBody = httpBody;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    @Override
    public String toString() {
        return "RequestLogData{" +
                "clientIp='" + clientIp + '\'' +
                ", methodName='" + methodName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", uri='" + uri + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", authorization='" + authorization + '\'' +
                ", httpBody=" + httpBody +
                ", costTime=" + costTime +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }
}
