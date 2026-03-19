package com.glory.USSD_Wallet_Project.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.glory.USSD_Wallet_Project.dto.TransactionDto;
import com.glory.USSD_Wallet_Project.model.Account;
import com.glory.USSD_Wallet_Project.model.BankAccount;
import com.glory.USSD_Wallet_Project.model.Transaction;
import com.glory.USSD_Wallet_Project.model.Transaction.TransactionType;
import com.glory.USSD_Wallet_Project.repository.AccountRepository;
import com.glory.USSD_Wallet_Project.repository.BankAccountRepository;
import com.glory.USSD_Wallet_Project.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class TransactionService {

   
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionDto.Response deposit(TransactionDto.DepositRequest request) {

        validateAmount(request.getAmount(), "Deposit");

        Account account = accountRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BankAccount bankAccount = getWallet(request.getPhoneNumber());

        bankAccount.setAccountBalance(
                bankAccount.getAccountBalance().add(request.getAmount())
        );
        bankAccountRepository.save(bankAccount);

        return saveTransaction(account, request.getAmount(), request.getDescription(), TransactionType.CREDIT);
    }

    @Transactional
    public TransactionDto.Response withdraw(TransactionDto.WithdrawRequest request) {

        validateAmount(request.getAmount(), "Withdrawal");

        Account account = accountRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BankAccount bankAccount = getWallet(request.getPhoneNumber());

        if (bankAccount.getAccountBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        bankAccount.setAccountBalance(
                bankAccount.getAccountBalance().subtract(request.getAmount())
        );
        bankAccountRepository.save(bankAccount);

        return saveTransaction(account, request.getAmount(), request.getDescription(), TransactionType.DEBIT);
    }

    public BigDecimal checkBalance(String phoneNumber) {
        return getWallet(phoneNumber).getAccountBalance();
    }

    public List<TransactionDto.Response> getTransactionHistory(String phoneNumber) {

        Account account = accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return transactionRepository
                .findByAccountAccountNumber(account.getAccountNumber())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BankAccount getWallet(String phoneNumber) {
        BankAccount bankAccount = bankAccountRepository.findByAccount_PhoneNumber(phoneNumber);
        if (bankAccount == null) {
            throw new IllegalArgumentException("Wallet not found");
        }
        return bankAccount;
    }

    private void validateAmount(BigDecimal amount, String type) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(type + " amount must be greater than zero");
        }
    }

    private TransactionDto.Response saveTransaction(Account account, BigDecimal amount,
                                                    String description, TransactionType type) {

        Transaction transaction = Transaction.builder()
                .account(account)
                .type(type)
                .amount(amount)
                .description(description)
                .reference(UUID.randomUUID().toString())
                .build();

        transactionRepository.save(transaction);
        return mapToResponse(transaction);
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