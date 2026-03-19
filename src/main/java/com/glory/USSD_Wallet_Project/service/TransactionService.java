package com.glory.USSD_Wallet_Project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.glory.USSD_Wallet_Project.dto.TransactionDto;
import com.glory.USSD_Wallet_Project.model.Account;
import com.glory.USSD_Wallet_Project.model.Transaction;
import com.glory.USSD_Wallet_Project.repository.AccountRepository;
import com.glory.USSD_Wallet_Project.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class TransactionService {

   
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


   
    public List<TransactionDto.Response> getTransactionHistory(String phoneNumber) {

        Account account = accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return transactionRepository
                .findByAccountAccountNumber(account.getAccountNumber())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    private TransactionDto.Response mapToResponse(Transaction transaction) {
        TransactionDto.Response response = new TransactionDto.Response();
        response.setId(transaction.getId());
        response.setType(transaction.getType().name());
        response.setAmount(transaction.getAmount());
        response.setDescription(transaction.getDescription());
        response.setReference(transaction.getReference());
        response.setTimestamp(transaction.getTransactionDate());
        return response;
    }
}