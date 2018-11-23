package com.spring.valid.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class SmsMessage implements Serializable {


    private static final long serialVersionUID = -8311671458112781796L;
    /**
     * 发送方编号
     */
    private String sender;

    /**
     * 接收方编号
     */
    private String receiver;

    /**
     * 发送者号码
     */
    private String senderMobile;


    /**
     * 接受者号码
     */
    private String receiverMobile;

    /**
     * 发送标志 1为销售 2为客户
     */
    private int senderRole;

    /**
     * 发送时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date occurTime;

    /**
     * 发送内容
     */
    @NotBlank
    private String content;

    /**
     * 签名
     */
    @NotBlank
    private String signature;

    /**
     * 发送者名字
     */
    private String senderName;

    /**
     * 接受者名字
     */
    private String receiverName;

    /**
     * 发送状态  销售发送的
     */
    @NotNull
    private int sendStatus;

    /**
     * 读取状态  客户回复的
     */
    @NotNull
    private int isRead;


    @Override
    public String toString() {
        return "SmsMessage{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", senderMobile='" + senderMobile + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", senderRole=" + senderRole +
                ", occurTime=" + occurTime +
                ", content='" + content + '\'' +
                ", signature='" + signature + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", sendStatus=" + sendStatus +
                ", isRead=" + isRead +
                '}';
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public int getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(int senderRole) {
        this.senderRole = senderRole;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {

        this.occurTime = occurTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }


}
