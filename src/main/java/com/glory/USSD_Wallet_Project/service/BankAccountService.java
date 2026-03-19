package com.glory.USSD_Wallet_Project.service;

import java.math.BigDecimal;
import java.util.UUID;

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


@Service
public class BankAccountService {

    
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

 public BankAccountService(BankAccountRepository bankAccountRepository, 
 AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountRepository =  accountRepository;
        this.transactionRepository = transactionRepository;
        
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

    public BankAccount getWallet(String phoneNumber) {
        BankAccount bankAccount = bankAccountRepository.findByAccount_PhoneNumber(phoneNumber);
        if (bankAccount == null) {
            throw new IllegalArgumentException("Wallet not found");
        }
        return bankAccount;
    }

    public void validateAmount(BigDecimal amount, String type) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(type + " amount must be greater than zero");
        }
    }

    public TransactionDto.Response saveTransaction(Account account, BigDecimal amount,
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
