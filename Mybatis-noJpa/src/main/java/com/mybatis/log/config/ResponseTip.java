package com.mybatis.log.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(value = "响应对象", description = "responseTip")
public class ResponseTip  implements Serializable {

    private static final long serialVersionUID = 1L;

    // 状态码:0是成功 1是失败
    @ApiModelProperty(value = "状态码:0是成功 1是失败", required = true)
    private int status;

    // 异常或其他主要提示信息
    @ApiModelProperty(value = "异常或其他主要提示信息", required = true)
    private String message;

    // 返回的具体数据
    @ApiModelProperty(value = "返回的具体数据", required = true)
    private Object data;

    public ResponseTip() {
        status = IConstants.RESULT_INT_ERROR;
        message = "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseTip{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
