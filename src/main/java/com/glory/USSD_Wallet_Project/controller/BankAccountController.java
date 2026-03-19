package com.glory.USSD_Wallet_Project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glory.USSD_Wallet_Project.model.Account;
import com.glory.USSD_Wallet_Project.repository.AccountRepository;
import com.glory.USSD_Wallet_Project.service.BankAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor

public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final AccountRepository accountRepository;

     @PostMapping("/create-wallet")
    public String createWallet(@RequestParam String phoneNumber) {

        Account account = accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bankAccountService.createWallet(account);
    }

    // @GetMapping("/balance")
    // public String getBalance(@RequestParam String phoneNumber) {
    //     return bankAccountService.getBalance(phoneNumber);
    // }
}



