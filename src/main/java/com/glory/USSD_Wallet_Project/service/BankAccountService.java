package com.glory.USSD_Wallet_Project.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.glory.USSD_Wallet_Project.model.Account;
import com.glory.USSD_Wallet_Project.model.BankAccount;
import com.glory.USSD_Wallet_Project.repository.BankAccountRepository;


@Service
public class BankAccountService {

private final BankAccountRepository bankAccountRepository;

 public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


   
    public String createWallet(Account account) {

        if (bankAccountRepository.existsByAccount(account)) {
            return "Wallet already exists for this user.";
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccount(account);

        bankAccount.setAccountNumber(account.getAccountNumber());

        bankAccount.setAccountBalance(BigDecimal.ZERO);

        bankAccountRepository.save(bankAccount);

        return "Wallet created successfully for account: " + account.getAccountNumber();
    }

    public String getBalance(String phoneNumber) {

        BankAccount bankAccount = bankAccountRepository
                .findByAccount_PhoneNumber(phoneNumber);

        if (bankAccount == null) {
            return "Wallet not found for this phone number.";
        }

        return "Your balance is: " + bankAccount.getAccountBalance();
    }

}
