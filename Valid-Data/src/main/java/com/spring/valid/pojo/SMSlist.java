package com.spring.valid.pojo;

import javax.validation.Valid;
import java.util.List;

public class SMSlist {

    @Valid
    private List<SmsMessage> smsMessages;

}
