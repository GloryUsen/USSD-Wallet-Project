package com.glory.USSD_Wallet_Project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glory.USSD_Wallet_Project.dto.AccountDto;
import com.glory.USSD_Wallet_Project.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ussd")
@RequiredArgsConstructor

public class AccountController {

      private final AccountService accountService;


    @PostMapping("/create-account")
    public String createAccount(@RequestBody AccountDto.CreateRequest request) {
        return accountService.createAccountUSSD(request);
    }

}
