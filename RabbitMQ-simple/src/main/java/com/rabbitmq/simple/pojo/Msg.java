package com.rabbitmq.simple.pojo;

/**
 * @Auther: Administrator
 * @Date: 2018/7/16 0016 10:22
 * @Description:
 */
public class Msg {

    private int code;

    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
