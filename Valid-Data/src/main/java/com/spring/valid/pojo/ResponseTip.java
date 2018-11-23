package com.spring.valid.pojo;


import java.io.Serializable;

public class ResponseTip implements Serializable {


    private static final long serialVersionUID = -1452813377857187240L;

    private int code;
    private String msg;
    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseTip() {
    }


    public  static  ResponseTip success(){
        return success(null);
    }

    public  static  ResponseTip success(Object data){
        ResponseTip tip=new ResponseTip();
        tip.code=200;
        tip.data=data;
        tip.msg="操作成功";
        return  tip;
    }

    public  static  ResponseTip error(int code,Object data){
        ResponseTip tip=new ResponseTip();
        tip.code=code;
        tip.data=data;
        tip.msg="操作失败";
        return  tip;

    }
}
