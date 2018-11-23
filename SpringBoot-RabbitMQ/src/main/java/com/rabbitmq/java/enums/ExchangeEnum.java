package com.rabbitmq.java.enums;
import lombok.Getter;

/**
 * @Auther: Administrator
 * @Date: 2018/7/10 0010 16:57
 * @Description:
 */
@Getter
public enum ExchangeEnum {


    /**
     * 用户注册交换配置枚举
     */
    USER_REGISTER("user.register.topic.exchange");

    private String value;

    ExchangeEnum(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
