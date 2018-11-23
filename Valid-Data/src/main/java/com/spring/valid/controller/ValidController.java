package com.spring.valid.controller;

import com.alibaba.fastjson.JSON;
import com.spring.valid.pojo.ResponseTip;
import com.spring.valid.pojo.SMSlist;
import com.spring.valid.pojo.SmsMessage;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ValidController {


    @PostMapping("/test1")
    public ResponseTip valid1(@RequestBody @Valid SmsMessage smsMessage, BindingResult result){

        ResponseTip responseTip=new ResponseTip();
        Map<String, Object> bindingResultMap = new HashMap<String, Object>();
        if (result.hasErrors()) {
            responseTip = getResponseTip(result, bindingResultMap);
        }else{
            responseTip=ResponseTip.success(JSON.toJSON(smsMessage));
        }
        return responseTip;
    }

    @PostMapping("/test2")
    public ResponseTip valid2(@RequestBody @Valid List<SmsMessage> smsMessage, BindingResult result){

        ResponseTip responseTip=new ResponseTip();
        Map<String, Object> bindingResultMap = new HashMap<String, Object>();
        if (result.hasErrors()) {
            responseTip = getResponseTip(result, bindingResultMap);
        }else{
            responseTip=ResponseTip.success(JSON.toJSON(smsMessage));
        }
        return responseTip;
    }


    @PostMapping("/test3")
    public ResponseTip valid3(@RequestBody @Valid SMSlist smSlist, BindingResult result){

        ResponseTip responseTip=new ResponseTip();
        Map<String, Object> bindingResultMap = new HashMap<String, Object>();
        if (result.hasErrors()) {
            responseTip = getResponseTip(result, bindingResultMap);
        }else{
            responseTip=ResponseTip.success(JSON.toJSON(smSlist));
        }
        return responseTip;
    }




    private ResponseTip getResponseTip(BindingResult result, Map<String, Object> bindingResultMap) {
        ResponseTip responseTip;List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            bindingResultMap.put(field,defaultMessage);
        }
        responseTip=ResponseTip.error(500, JSON.toJSON(bindingResultMap));
        return responseTip;
    }
}
