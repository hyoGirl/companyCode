package com.spring.transaction.controller;


import com.spring.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    AccountService accountService;


//    @PostMapping("/update")
//    public String toupdateAccount(@RequestParam("money") double money,@RequestParam("id") int id){
//
//        long seqId = IdWorker.getId();
//        System.out.println(seqId+"**************************");
////        String data=   accountService.updateAccount(id,money,seqId);
//
//        return "********";
//    }



}
