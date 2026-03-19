package com.glory.USSD_Wallet_Project.service;

import org.springframework.stereotype.Service;

import com.glory.USSD_Wallet_Project.dto.AccountDto;
import com.glory.USSD_Wallet_Project.model.Account;
import com.glory.USSD_Wallet_Project.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    
    public String createAccountUSSD(AccountDto.CreateRequest request) {

        if (accountRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return "An account already exists with this phone number.";
        }

        String accountNumber = "ACC" + request.getPhoneNumber()
                .substring(Math.max(0, request.getPhoneNumber().length() - 6));

        Account account = Account.builder()
                 .phoneNumber(request.getPhoneNumber())
                 .accountNumber(accountNumber)
                
                .build();

        accountRepository.save(account);
        return "Welcome to USSD Wallet! Your account has been created. Your account number is: " + accountNumber;
    }
}
